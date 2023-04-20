package com.paws.photoapplication.ui.chipInput

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View.OnFocusChangeListener
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputLayout
import com.paws.photoapplication.R


@BindingAdapter("chips")
fun setChips(chipInputView: ChipInputView, chips: List<String>?) {
        // Clear existing chips
        chipInputView.chipGroup.removeAllViews()

        // Add new chips
   val copy = ArrayList(chips)

    if (chips != null) {
        for (chip in copy) {
            chipInputView.addNewChip(chip, false)
        }
    }
    }


class ChipInputView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    val chipGroup: ChipGroup
    var onChipAdded: ((chipText: String) -> Unit)? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.input_chip_view, this, true)

        val inputLayout = findViewById<TextInputLayout>(R.id.i_input_v)
        val editText = findViewById<AutoCompleteTextView>(R.id.i_input_e)
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

        val adapter = AutoCompleteAdapter(editText.context, R.layout.autocomplete_tag_item, editText.text.toString())
        editText.setAdapter(adapter)
        editText.setOnItemClickListener { _, _, _, _ ->
            afterTextChanged(editText, adapter, true)
        }


        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
                afterTextChanged(editText, adapter, false)
            }
        })

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ChipInputView,
            0, 0).apply {

            try {
                var edit = getString(R.styleable.ChipInputView_EditTextHint)
                var label = getString(R.styleable.ChipInputView_LabelTextHint)
                inputLayout.hint = label
                editText.hint = edit
            } finally {
                recycle()
            }
        }
    }

    fun addNewChip(text: String, notify: Boolean) {
        val newChip = LayoutInflater.from(context).inflate(R.layout.input_chip_item, chipGroup, false)
        val bind = newChip.findViewById<Chip>(R.id.chip)
        bind.text = text
        bind.setOnCloseIconClickListener {
            chipGroup.removeView(newChip)
        }
        chipGroup.addView(newChip)

        if(notify) {
            onChipAdded?.invoke(text)
        }
    }
    fun afterTextChanged(editText: EditText, adapter: AutoCompleteAdapter, ignoreCase : Boolean) {
        val text = editText.text.toString()
        if (text.endsWith("\n") || ignoreCase) {
            val filteredText = text.removeSuffix("\n").filter { it.isLetterOrDigit()}
            if(filteredText.isNotEmpty())
                addNewChip(filteredText, true)
            editText.setText("")
        }
        adapter.suggest = text
        adapter.notifyDataSetChanged()
    }


}
