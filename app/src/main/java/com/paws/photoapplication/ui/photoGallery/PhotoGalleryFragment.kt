package com.paws.photoapplication.ui.photoGallery

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.paws.photoapplication.R
import com.paws.photoapplication.databinding.FragmentPhotoGalleryBinding
import com.paws.photoapplication.ui.optionsDialog.OptionsBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PhotoGalleryFragment : Fragment(R.layout.fragment_photo_gallery) {
    private var _binding: FragmentPhotoGalleryBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: PhotoGalleryViewModel by activityViewModels()
    private lateinit var photoAdapter: PhotoAdapter
    private lateinit var suggestionAdapter: SuggestionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotoGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("Range")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.setLifecycleOwner(viewLifecycleOwner)

        photoAdapter = PhotoAdapter(emptyList())
        photoAdapter.onItemClick = { photo, position ->
            val action =
                PhotoGalleryFragmentDirections.actionPhotoGalleryFragmentToPhotoView(position)
            findNavController().navigate(action)
        }
        binding.photoDisplay.adapter = photoAdapter
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.photoDisplay.layoutManager = layoutManager

        binding.addPhotoButton.setOnClickListener {
            val action = PhotoGalleryFragmentDirections.actionPhotoGalleryFragmentToPhotoCapture()
            findNavController().navigate(action)
        }

        lifecycleScope.launch {
            viewModel.photos.collect {
                photoAdapter.photos = it
                photoAdapter.notifyDataSetChanged()
            }
        }

        viewModel.tagsFilter.observe(viewLifecycleOwner) {
            viewModel.setDescriptionFilter(binding.searchView.editText.text.toString())
            hideSearchView()
        }

        viewModel.descriptionFilter.observe(viewLifecycleOwner) {
            hideSearchView()
        }

        viewModel.suggestions.observe(viewLifecycleOwner) {
            suggestionAdapter.suggestions = it
            suggestionAdapter.notifyDataSetChanged()
        }

        binding.tagsChip.setOnClickListener {
            showOptionsDialog()
        }

        binding.searchBar.setOnMenuItemClickListener {

            when (it.itemId) {
                R.id.action_tags_search -> {
                    showOptionsDialog()
                    true
                }
                else -> false
            }
        }

        suggestionAdapter = SuggestionAdapter("", emptyList())
        suggestionAdapter.onItemClick = {
            binding.searchView.editText.setText(it)
            viewModel.setDescriptionFilter(it)
        }
        binding.svSuggestions.adapter = suggestionAdapter
        binding.svSuggestions.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.searchView.editText.setOnEditorActionListener { tv, _, _ ->
            viewModel.setDescriptionFilter(tv.text.toString())
            false
        }

        binding.searchView.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                suggestionAdapter.currentSearch = p0.toString()
                suggestionAdapter.filter.filter(p0.toString())
            }
    })
    }

    private fun hideSearchView() {
        binding.searchView.hide()
        viewModel.search()
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