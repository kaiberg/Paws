package com.example.myapplication

import android.os.Bundle
import android.view.*
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.myapplication.databinding.FragmentPhotoViewBinding
import com.google.android.material.chip.Chip

class PhotoViewFragment : Fragment(R.layout.fragment_photo_view) {
    private var _binding: FragmentPhotoViewBinding? = null
    private val binding
        get() = _binding!!
    private val args: PhotoViewFragmentArgs by navArgs()
    private lateinit var photo: photo2
    private var position: Int = 0
        set(value) {
            if (isInRange(value)) {
                field = value
                setPhoto(field)
                resetMenu(binding.toolbar.menu)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.inflateMenu(R.menu.photo_view_menu)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_last -> {
                    position -= 1
                    true
                }
                R.id.action_next -> {
                    position += 1
                    true
                }
                else -> true
            }
        }
        position = args.position
    }

    private fun resetMenu(menu: Menu) {
        var menuItem = menu.findItem(R.id.action_next)
        if (menuItem != null) {
            if (!isInRange(position + 1)) {
                disableMenuItem(menuItem)
            }
            else
                enableMenuItem(menuItem, R.drawable.baseline_arrow_forward_white_24)
        }

        menuItem = menu.findItem(R.id.action_last)
        if (menuItem != null) {
            if (!isInRange(position - 1)) {
                disableMenuItem(menuItem)
            }
            else
                enableMenuItem(menuItem, R.drawable.baseline_arrow_back_white_24)
        }

    }

    private fun isInRange(index: Int): Boolean {
        val size = DataManager.photos2.value?.size ?: return false
        return (index in 0 until size)
    }

    private fun enableMenuItem(menuItem: MenuItem, imageId : Int) {
        menuItem.isEnabled = true
        menuItem.icon = getDrawable(requireContext(), imageId)
    }

    private fun disableMenuItem(item: MenuItem) {
        item.isEnabled = false
        item.icon = getDrawable(requireContext(), R.drawable.baseline_block_white_24)
    }

    private fun setPhoto(index: Int) {
        if (!isInRange(index))
            return

        photo = DataManager.photos2.value!![index]
        binding.imageView.load(photo.path)
        binding.description.text = photo.description
        binding.keywordsList.removeAllViews()

        for (keyword in photo.keyWords) {
            val chip = LayoutInflater.from(requireContext()).inflate(R.layout.view_chip_item, binding.keywordsList, false)
            val chipBind = chip.findViewById<Chip>(R.id.displayChip)
            chipBind.text = keyword

            binding.keywordsList.addView(chip)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
