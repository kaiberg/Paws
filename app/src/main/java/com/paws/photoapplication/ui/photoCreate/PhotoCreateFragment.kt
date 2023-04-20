package com.paws.photoapplication.ui.photoCreate

import android.graphics.Rect
import android.os.Bundle
import android.view.*
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.paws.photoapplication.R
import com.paws.photoapplication.databinding.FragmentPhotoCreateBinding
import dagger.hilt.android.AndroidEntryPoint


@BindingAdapter("imageUrl")
fun loadImage(view : ImageView?, imageUrl: String?) {
    if(imageUrl == null) {
        return
    }
    if(imageUrl.isNotEmpty())
        view?.load(imageUrl)
}



@AndroidEntryPoint
class PhotoCreateFragment() : Fragment(R.layout.fragment_photo_create) {
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

        binding.changeButton.setOnClickListener {
            changePhoto()
        }

        binding.tags.onChipAdded = { text : String ->
            viewModel.tags.add(text)
        }


        return binding.root
    }

    private fun changePhoto() {
        val action = PhotoCreateFragmentDirections.actionPhotoCreateFragmentToPhotoCapture()
        findNavController().navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            val action = PhotoCreateFragmentDirections.actionPhotoCreateFragmentSelf()
            findNavController().navigate(action)
        }

        val rootView = binding.root
        rootView.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val r = Rect()
                rootView.getWindowVisibleDisplayFrame(r)
                val screenHeight = rootView.height
                val keyboardHeight = screenHeight - r.bottom
                if (keyboardHeight > screenHeight * 0.15) { // keyboard is open
                } else { // keyboard is closed
                }
            }
        })


        // appbar closed
        binding.createAppbarLayout.addOnOffsetChangedListener({ appBarLayout, verticalOffset ->
            val mi = binding.toolbar.menu.findItem(R.id.action_change_picture)
            val isAppBarClosed = (verticalOffset == -binding.createColToolbarLayout.height + binding.toolbar.height)
                mi.isVisible = isAppBarClosed
        })

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_create_picture -> {
                    viewModel.createPhoto()

                    val action =
                        PhotoCreateFragmentDirections.actionPhotoCreateFragmentToPhotoGalleryFragment()
                    findNavController().navigate(action)
                    true
                }
                R.id.action_change_picture -> {
                    changePhoto()
                    true
                }
                else -> true
            }
        }
    }

}