package com.example.myapplication.ui

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.myapplication.R
import com.example.myapplication.ui.viewmodels.PhotoCreateViewModel
import com.example.myapplication.databinding.FragmentPhotoCreateBinding

@BindingAdapter("imageUrl")
fun loadImage(view : ImageView?, imageUrl: String?) {
    if(imageUrl == null) {
        return
    }
    if(imageUrl.isNotEmpty())
        view?.load(imageUrl)
}


class PhotoCreateFragment : Fragment(R.layout.fragment_photo_create) {
    private var _binding : FragmentPhotoCreateBinding? = null
    private val binding
        get() = _binding!!
    private val args: PhotoCreateFragmentArgs by navArgs()

    val viewModel: PhotoCreateViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotoCreateBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel

        viewModel.photoURL = args.imageUri
        loadImage(binding.selectedImage, args.imageUri)

        binding.createButton.setOnClickListener {
            viewModel.createPhoto()

            val action =
                PhotoCreateFragmentDirections.actionPhotoCreateFragmentToPhotoGalleryFragment()
            findNavController().navigate(action)
        }

        binding.changeButton.setOnClickListener {
            d("test1223", "${viewModel.description}")
            val action = PhotoCreateFragmentDirections.actionPhotoCreateFragmentToPhotoCapture()
            findNavController().navigate(action)
        }

        binding.tags.onChipAdded = { text : String ->
            viewModel.tags.add(text)
        }


        return binding.root
    }

}