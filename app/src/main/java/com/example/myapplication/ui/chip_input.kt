package com.example.myapplication.ui

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.example.myapplication.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("chips")
fun setTags(tagInputView: TagInputView, tags: List<String>?) {
        // Clear existing chips
        tagInputView.chipGroup.removeAllViews()

        // Add new chips
   val copy = ArrayList(tags)

    if (tags != null) {
        for (tag in copy) {
            tagInputView.addNewChip(tag)
        }
    }
    }


class TagInputView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    val chipGroup: ChipGroup
    var onChipAdded: ((chipText: String) -> Unit)? = null


    init {
        LayoutInflater.from(context).inflate(R.layout.input_chip_view, this, true)

        val inputLayout = findViewById<TextInputLayout>(R.id.i_input_v)
        val editText = inputLayout.editText!!
        chipGroup = findViewById(R.id.i_flex_box)

        editText.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                if (editText.text.toString() == " "){
                    editText.text.clear()
                }
            } else {
                if (editText.text.isNullOrEmpty() && chipGroup.childCount > 0) {
                    editText.setText(" ")
                }
            }
        }

        editText.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && editText.text.toString() == "") {
                // onBackspacePressed, also edittext is empty
                if (chipGroup.childCount <= 0) return@setOnKeyListener false
                val lastChip = chipGroup.getChildAt(chipGroup.childCount - 1) as Chip
                chipGroup.removeView(lastChip)
            }
            false
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                val text = editable.toString()
                if (text.isNotEmpty() && text.endsWith("\n")) {
                    addNewChip(text.removeSuffix("\n").filter { it.isLetterOrDigit() })
                    inputLayout.editText!!.setText("")
                }
            }
        })
    }

    fun addNewChip(text: String) {
        val newChip = LayoutInflater.from(context).inflate(R.layout.input_chip_item, chipGroup, false)
        val bind = newChip.findViewById<Chip>(R.id.chip)
        bind.text = text
        bind.setOnCloseIconClickListener {
            chipGroup.removeView(newChip)
        }
        chipGroup.addView(newChip)

        onChipAdded?.invoke(text)
    }


}
