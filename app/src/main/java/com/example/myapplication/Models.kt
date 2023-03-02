package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.myapplication.network.DogApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

data class photo(val path: String) {}

object DataManager {
    private val _photos = MutableLiveData<List<photo>>(emptyList())
    val photos: LiveData<List<photo>> = _photos

    init {
        SeedData()
    }

    private fun SeedData() {
        GlobalScope.launch {
            val photoSet = DogApi.retrofitService.GetPhotos(50).message.map { photo(it) }
            _photos.postValue(photoSet)
        }
    }
}


class PhotoAdapter(var photos: List<photo>) : RecyclerView.Adapter<PhotoAdapter.PhotoHolder>() {
    var onItemClick: ((photo, position : Int) -> Unit)? = null
    inner class PhotoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var currentPosition: Int = -1
        var currentPhoto: photo? = null
        var imageView: ImageView = itemView.findViewById(R.id.imvPhoto)

        fun setPhoto(position: Int) {
            this.currentPosition = position
            this.currentPhoto = photos[position]

            imageView.load(this.currentPhoto!!.path)

          imageView.setOnClickListener {
              onItemClick?.invoke(this.currentPhoto!!, this.currentPosition)
          }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoAdapter.PhotoHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.photo_list_item, parent, false)
        return PhotoHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoAdapter.PhotoHolder, position: Int) {
        holder.setPhoto(position)
    }

    override fun getItemCount(): Int = photos.size
}