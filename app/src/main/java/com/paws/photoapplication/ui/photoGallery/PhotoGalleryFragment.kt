package com.paws.photoapplication.ui.photoGallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.paws.photoapplication.R
import com.paws.photoapplication.databinding.FragmentPhotoGalleryBinding
import com.paws.photoapplication.ui.optionsDialog.OptionsBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
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

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.photos.collect {
                adapter.photos = it
                adapter.notifyDataSetChanged()

            }
        }

        binding.searchView.editText.setOnEditorActionListener { textView, i, keyEvent ->
            viewModel.descriptionFilter = binding.searchView.text.toString()
            binding.searchView.hide()
            binding.searchBar.text = viewModel.descriptionFilter
            viewModel.search()
            false
        }

        binding.tagsChip.setOnClickListener {
            showOptionsDialog()
        }
        /*binding.searchView.setupWithSearchBar(binding.searchBar)*/
    }

    fun showOptionsDialog() {
        val bottomSheet = OptionsBottomSheetDialogFragment()
        bottomSheet.show(parentFragmentManager, bottomSheet.tag)
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}