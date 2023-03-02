package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.databinding.FragmentPhotoGalleryBinding

class PhotoGalleryFragment : Fragment(R.layout.fragment_photo_gallery){
    private var _binding : FragmentPhotoGalleryBinding? = null
    private val binding
    get() = _binding!!

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

        adapter = PhotoAdapter(DataManager.photos.value!!)
        adapter.onItemClick = { photo, position ->
            val intent = Intent(requireContext(), PhotoView::class.java)
            intent.putExtra(EXTRA_PHOTO_POSITION, position)
            this.startActivity(intent)
        }
        binding.photoDisplay.adapter = adapter
        val layoutManager = GridLayoutManager(requireContext(), calculateNoOfColumns(requireContext(), 100.toFloat()))
        binding.photoDisplay.layoutManager = layoutManager


        binding.addPhotoButton.setOnClickListener {
            // start photo taking activity
            val intent = Intent(requireContext(), PhotoCapture::class.java)
            this.startActivity(intent)
        }

        DataManager.photos.observe(viewLifecycleOwner) {
            adapter.photos = it
            adapter.notifyDataSetChanged()
        }

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