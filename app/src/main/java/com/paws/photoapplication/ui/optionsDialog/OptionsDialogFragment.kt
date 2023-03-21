package com.paws.photoapplication.ui.optionsDialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.paws.photoapplication.R
import com.paws.photoapplication.databinding.FragmentOptionsDialogBinding
import com.paws.photoapplication.databinding.FragmentPhotoGalleryBinding
import com.paws.photoapplication.ui.photoGallery.PhotoAdapter
import com.paws.photoapplication.ui.photoGallery.PhotoGalleryViewModel

class OptionsDialogFragment : DialogFragment(R.layout.fragment_options_dialog) {
    private var _binding : FragmentOptionsDialogBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: TagsViewModel by viewModels()
    private lateinit var adapter: OptionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOptionsDialogBinding.inflate(inflater, container, false)
        adapter = OptionsAdapter(viewModel.tags)

        binding.optionsRecyclerView.adapter = adapter
        // Inflate the layout to use as dialog or embedded fragment
        return binding.root
    }


}