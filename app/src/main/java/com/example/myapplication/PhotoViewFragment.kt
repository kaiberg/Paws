package com.example.myapplication

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.myapplication.databinding.FragmentPhotoViewBinding

class PhotoViewFragment : Fragment(R.layout.fragment_photo_view) {
    private var _binding : FragmentPhotoViewBinding? = null
    private val binding
        get() = _binding!!
    private val args: PhotoViewFragmentArgs by navArgs()
    private var item_position: Int = 0
    set(value) {
        if(isInRange(value)) {
            field = value
            showImage(field)
/*
            invalidateOptionsMenu(Activity())
*/
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotoViewBinding.inflate(inflater, container, false)

        item_position = args.position
        return binding.root
    }

/*    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.photo_menu, menu)
        return true
    }*/

/*    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if(!isInRange(item_position+1)) {
            val menuItem = menu?.findItem(R.id.action_next)
            if(menuItem != null)
                disableMenuItem(menuItem)
        }

        if(!isInRange(item_position-1)) {
            val menuItem = menu?.findItem(R.id.action_last)
            if(menuItem != null)
                disableMenuItem(menuItem)
        }

        return super.onPrepareOptionsMenu(menu)
    }*/

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_last -> {
                item_position-=1
                true
            }
            R.id.action_next -> {
                item_position+=1
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun isInRange(index: Int) : Boolean {
        val size = DataManager.photos.value?.size ?: return false
        return (index in 0 until size)
    }

    private fun disableMenuItem(item: MenuItem) {
        item.isEnabled = false
/*
        item.icon = getDrawable(R.drawable.baseline_block_white_24)
*/
    }

    private fun showImage(index: Int) {
        if(!isInRange(index))
            return

        val photo = DataManager.photos.value!![index]
        val image = binding.imageView

        image.load(photo.path)
    }
}