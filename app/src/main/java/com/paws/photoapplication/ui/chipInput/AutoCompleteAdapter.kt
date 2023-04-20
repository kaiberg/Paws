package com.paws.photoapplication.ui.chipInput

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.paws.photoapplication.R

class AutoCompleteAdapter(context: Context, val resource: Int, var suggest: String) :
    ArrayAdapter<String>(context, resource) {

    inner class ViewHolder(view: View) {
        val title: TextView = view.findViewById(R.id.text_title)
        val text: TextView = view.findViewById(R.id.text_view)

        init {
            title.text = "Add tag"
        }
    }

    override fun getCount(): Int {
        return 1
    }

    override fun getItem(position: Int): String {
        return suggest
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val viewHolder: ViewHolder

        if (view == null) {
            view = LayoutInflater.from(parent.context).inflate(resource, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else
            viewHolder = view.tag as ViewHolder
        viewHolder.text.text = suggest
        return view!!
    }


}


