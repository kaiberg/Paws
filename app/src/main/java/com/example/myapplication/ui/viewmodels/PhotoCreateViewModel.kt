package com.example.myapplication.ui.viewmodels

import android.util.Log.d
import androidx.lifecycle.ViewModel
import com.example.myapplication.ui.DataManager
import com.example.myapplication.ui.photo

class PhotoCreateViewModel : ViewModel() {
    init {
        d("photoInit", "NEW VM created")
    }

    fun createPhoto() {
        DataManager.photos.add(photo(description,tags, photoURL))
        d("photoInit", "${description}, ${tags}")

        description = ""
        photoURL = ""
        tags = arrayListOf()
    }

    var description: String = ""
    var photoURL: String = ""
    var tags: ArrayList<String> = arrayListOf()
}
