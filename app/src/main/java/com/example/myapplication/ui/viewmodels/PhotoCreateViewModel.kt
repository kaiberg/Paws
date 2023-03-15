package com.example.myapplication.ui.viewmodels

import android.util.Log.d
import androidx.lifecycle.ViewModel

class PhotoCreateViewModel : ViewModel() {
    init {
        d("photoInit", "NEW VM created")
    }

    fun createPhoto() {
/*
        DataManager.photos2.add(photo2(description,photoURL, tags))
*/
        description = ""
        photoURL = ""
        tags = arrayListOf()
    }

    var description: String = ""
    var photoURL: String = ""
    var tags: ArrayList<String> = arrayListOf()
}
