package com.paws.photoapplication.ui.photoGallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.paws.photoapplication.R
import com.paws.photoapplication.data.model.Photo
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


class PhotoAdapter(var photos: List<Photo>) : RecyclerView.Adapter<PhotoAdapter.PhotoHolder>() {
    var onItemClick: ((photo : Photo, position : Int) -> Unit)? = null
    inner class PhotoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var currentPosition: Int = -1
        var currentPhoto: Photo? = null
        var imageView: ImageView = itemView.findViewById(R.id.imv_photo)
        var descriptionText : TextView = itemView.findViewById(R.id.txv_description)
        var keywordsChipGroup: ChipGroup = itemView.findViewById(R.id.cg_keywords)

        fun setPhoto(position: Int) {
            this.currentPosition = position
            this.currentPhoto = photos[position]

            imageView.load(this.currentPhoto!!.path)
            descriptionText.setText(currentPhoto!!.description)
            keywordsChipGroup.removeAllViews()
            for(keyword in currentPhoto!!.tags) {
                val chip = LayoutInflater.from(itemView.context).inflate(R.layout.view_chip_item, keywordsChipGroup, false)
                val chipBind = chip.findViewById<Chip>(R.id.displayChip)
                chipBind.text = keyword

                keywordsChipGroup.addView(chip)
            }

            imageView.setOnClickListener {
                onItemClick?.invoke(this.currentPhoto!!, this.currentPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.photo_list_item, parent, false)
        return PhotoHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        holder.setPhoto(position)
    }

    override fun getItemCount(): Int = photos.size
}