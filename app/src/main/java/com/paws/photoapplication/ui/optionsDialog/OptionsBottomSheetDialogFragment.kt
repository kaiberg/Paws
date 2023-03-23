package com.paws.photoapplication.ui.optionsDialog

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paws.photoapplication.R
import com.paws.photoapplication.databinding.FragmentOptionsDialogBinding
import com.paws.photoapplication.ui.photoGallery.PhotoGalleryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OptionsBottomSheetDialogFragment : BottomSheetDialogFragment(R.layout.fragment_options_dialog) {
    private var _binding : FragmentOptionsDialogBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: OptionsViewModel by viewModels()
    val galleryViewModel: PhotoGalleryViewModel by activityViewModels()
    private lateinit var adapter: OptionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOptionsDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.options.observe(viewLifecycleOwner) {
            for(item in it.filter { galleryViewModel.tagsFilter.contains(it.name) })
                item.isChecked = true

            adapter = OptionsAdapter(ArrayList(it))
            binding.optionsRecyclerView.adapter = adapter
            binding.optionsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }

        val bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        binding.optionsBottomSheet.minimumHeight = Resources.getSystem().displayMetrics.heightPixels

        binding.editTextOptionFilter.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                adapter.filter.filter(p0.toString())
            }
        })

        binding.editTextExit.setOnClickListener {
            dismiss()
        }

        binding.editTextApply.setOnClickListener {
            val selectedTags = viewModel.options.value!!.filter { it.isChecked }.map { it.name }
            galleryViewModel.tagsFilter = selectedTags
            dismiss()
        }
    }


}