package com.paws.photoapplication.ui.photoCreate

import androidx.lifecycle.ViewModel
import com.paws.photoapplication.data.model.Photo
import com.paws.photoapplication.data.repository.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotoCreateViewModel @Inject constructor(private val repository: PhotoRepository) : ViewModel() {
    fun createPhoto() {
        repository.add(Photo(photoURL,tags, description))

        description = ""
        photoURL = ""
        tags = arrayListOf()
    }

    var description: String = ""
    var photoURL: String = ""
    var tags: ArrayList<String> = arrayListOf()
}
