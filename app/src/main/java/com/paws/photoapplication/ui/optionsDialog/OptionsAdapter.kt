package com.paws.photoapplication.ui.optionsDialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.paws.photoapplication.R

data class Option(val name: String, var isChecked : Boolean)

class OptionsAdapter(val optionsList : List<String>) : RecyclerView.Adapter<OptionsAdapter.ItemViewHolder>() {
    private val options : List<Option> = optionsList.map { Option(it, false) }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OptionsAdapter.ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.options_dialog_item, parent, false))
    }

    override fun onBindViewHolder(holder: OptionsAdapter.ItemViewHolder, position: Int) {
        holder.setData(position)
    }

    override fun getItemCount(): Int {
        return optionsList.size
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var currentOption : Option
        lateinit var checkbox : CheckBox
        fun setData(position : Int) {
            currentOption = options[position]
            checkbox = itemView.findViewById(R.id.options_checkbox)
            checkbox.text = currentOption.name
            checkbox.isChecked = currentOption.isChecked

            checkbox.setOnCheckedChangeListener { view, isChecked -> currentOption.isChecked = isChecked}
        }
    }
}