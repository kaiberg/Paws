package com.example.myapplication.ui.photoGallery

import androidx.lifecycle.*
import com.example.myapplication.data.model.Photo
import com.example.myapplication.data.repository.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoGalleryViewModel @Inject constructor(private val repository: PhotoRepository) : ViewModel() {

    private val _photos: MutableStateFlow<List<Photo>> = MutableStateFlow(emptyList())
    val photos: StateFlow<List<Photo>> = _photos.asStateFlow()

    var descriptionFilter: String = ""
    var tagsFilter: List<String> = emptyList()

    init {
        viewModelScope.launch {
            repository.getPhotos().collect { photos ->
                val filteredPhotos = filterPhotos(photos)
                _photos.value = filteredPhotos
            }
        }
    }

    fun search() {
        val allPhotos = _photos.value
        val filteredPhotos = filterPhotos(allPhotos)
        _photos.value = filteredPhotos
    }

    private fun filterPhotos(photos: List<Photo>): List<Photo> {
        return photos.filter {
            it.description.uppercase().contains(descriptionFilter.uppercase()) &&
                    it.tags.containsAll(tagsFilter)
        }
    }
}
