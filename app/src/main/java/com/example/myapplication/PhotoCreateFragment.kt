package com.example.myapplication

import android.os.Bundle
import android.text.Editable
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.ImageSpan
import android.util.Log.d
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.getSpans
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.myapplication.databinding.FragmentPhotoCreateBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import java.io.File

class PhotoCreateFragment : Fragment(R.layout.fragment_photo_create) {
    private var _binding : FragmentPhotoCreateBinding? = null
    private val binding
        get() = _binding!!
    private var spanOffset = 0
    private val args: PhotoCreateFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotoCreateBinding.inflate(inflater, container, false)

        binding.selectedImage.load(args.imageUri)

        binding.createButton.setOnClickListener {
            val sb = StringBuilder()
            binding.keywords.chipGroup.forEach { view -> sb.append("text: ${(view as Chip).text}, " ) }
            d("chips", sb.toString())
        }

        binding.changeButton.setOnClickListener {
            val action = PhotoCreateFragmentDirections.actionPhotoCreateFragmentToPhotoCapture()
            findNavController().navigate(action)
        }


        return binding.root
    }
}