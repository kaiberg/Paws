package com.paws.photoapplication.ui.optionsDialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.paws.photoapplication.R
import com.paws.photoapplication.data.model.Option
import kotlin.collections.ArrayList

class OptionsAdapter(var optionsList : ArrayList<Option>) : RecyclerView.Adapter<OptionsAdapter.ItemViewHolder>(), Filterable {
    private val fullOptionsList = ArrayList(optionsList)
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
            currentOption = optionsList[position]
            checkbox = itemView.findViewById(R.id.options_checkbox)
            checkbox.text = currentOption.name
            checkbox.isChecked = currentOption.isChecked

            checkbox.setOnCheckedChangeListener { view, isChecked -> currentOption.isChecked = isChecked}
        }
    }

    override fun getFilter(): Filter {
        return optionsFilter
    }

    private val optionsFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<Option> = ArrayList()
            if (constraint.isEmpty()) {
                filteredList.addAll(fullOptionsList)
            } else {
                val predicate = constraint.toString().trim()
                for (item in fullOptionsList) {
                    if (item.name.contains(predicate, ignoreCase = true)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            optionsList.clear()
            optionsList.addAll(results.values as Collection<Option>)
            notifyDataSetChanged()
        }
    }
}