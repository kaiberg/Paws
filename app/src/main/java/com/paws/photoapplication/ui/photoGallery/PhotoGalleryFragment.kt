package com.paws.photoapplication.ui.photoGallery

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import android.provider.SearchRecentSuggestions
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
            hideSearchView()
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


        val suggestionList = ArrayList<String>()
        val uriBuilder = Uri.Builder()
            .scheme(ContentResolver.SCHEME_CONTENT)
            .authority(SearchDescriptionSuggestionProvider.AUTHORITY)

        uriBuilder.appendPath(SearchManager.SUGGEST_URI_PATH_QUERY)

        val selection = " ?"
        val selArgs = arrayOf("")

        val uri = uriBuilder.build()
        val cursor = requireActivity().contentResolver?.query(uri, null, selection, selArgs, null)

        if (cursor != null && cursor.moveToFirst()) {
            do {
                val suggestion = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
                suggestionList.add(suggestion)
            } while (cursor.moveToNext())
        }

        cursor?.close()

        suggestionAdapter = SuggestionAdapter("",suggestionList)
        suggestionAdapter.onItemClick = {
            binding.searchView.editText.setText(it)
            hideSearchView()
        }
        binding.svSuggestions.adapter = suggestionAdapter
        binding.svSuggestions.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)



        binding.searchView.editText.setOnEditorActionListener { textView, i, keyEvent ->
            hideSearchView()
            false
        }

        binding.searchView.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                suggestionAdapter.currentSearch = p0.toString()
                suggestionAdapter.notifyDataSetChanged()
                suggestionAdapter.filter.filter(p0.toString())
            }
    })
    }

    private fun hideSearchView() {
        val descriptionFilter = binding.searchView.editText.text.toString()
        viewModel.setDescriptionFilter(descriptionFilter)
        binding.searchView.hide()
        viewModel.search()

        // add filter to query history
        if(descriptionFilter == "")
            return

        SearchRecentSuggestions(
            requireContext(),
            SearchDescriptionSuggestionProvider.AUTHORITY,
            SearchDescriptionSuggestionProvider.MODE
        )
            .saveRecentQuery(descriptionFilter, null)
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