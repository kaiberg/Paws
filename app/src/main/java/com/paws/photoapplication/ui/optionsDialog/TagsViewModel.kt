package com.paws.photoapplication.ui.optionsDialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paws.photoapplication.data.model.Photo
import com.paws.photoapplication.data.repository.NolekPhotoRepository
import com.paws.photoapplication.data.repository.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagsViewModel @Inject constructor(private val photoRepository: PhotoRepository): ViewModel() {
    init{
        viewModelScope.launch {
            photoRepository.getPhotos().collect { tags = it.flatMap { tags }.distinct()}
        }
    }
     lateinit var tags : List<String>
}