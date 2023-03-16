package com.example.myapplication.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.myapplication.ui.PhotoRepository
import com.example.myapplication.ui.photo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotoCreateViewModel @Inject constructor(private val repository: PhotoRepository) : ViewModel() {
    fun createPhoto() {
        repository.add(photo(photoURL,tags, description))

        description = ""
        photoURL = ""
        tags = arrayListOf()
    }

    var description: String = ""
    var photoURL: String = ""
    var tags: ArrayList<String> = arrayListOf()
}
