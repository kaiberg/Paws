package com.example.myapplication.ui

import android.content.Context
import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentPhotoGalleryBinding
import com.example.myapplication.ui.viewmodels.PhotoGalleryViewModel

class PhotoGalleryFragment : Fragment(R.layout.fragment_photo_gallery){
    private var _binding : FragmentPhotoGalleryBinding? = null
    private val binding
    get() = _binding!!

    private val viewModel: PhotoGalleryViewModel by activityViewModels()
    private lateinit var adapter: PhotoAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotoGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PhotoAdapter(emptyList())
        adapter.onItemClick = { photo, position ->
            val action =
                PhotoGalleryFragmentDirections.actionPhotoGalleryFragmentToPhotoView(position)
            findNavController().navigate(action)
        }
        binding.photoDisplay.adapter = adapter
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.photoDisplay.layoutManager = layoutManager


        binding.addPhotoButton.setOnClickListener {
            val action = PhotoGalleryFragmentDirections.actionPhotoGalleryFragmentToPhotoCapture()
            findNavController().navigate(action)
        }

        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.photos = it
            adapter.notifyDataSetChanged()
            d("observable" ,"updated adapter to size:${it.size}")
        }

        /*binding.searchView.setupWithSearchBar(binding.searchBar)*/

    }

    fun calculateNoOfColumns(
        context: Context,
        columnWidthDp: Float
    ): Int {
        val displayMetrics = context.resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        return (screenWidthDp / columnWidthDp + 0.5).toInt()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}