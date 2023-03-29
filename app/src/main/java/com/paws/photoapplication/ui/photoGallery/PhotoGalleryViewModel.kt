package com.paws.photoapplication.ui.photoGallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paws.photoapplication.data.model.Photo
import com.paws.photoapplication.data.repository.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PhotoGalleryViewModel @Inject constructor(private val repository: PhotoRepository) : ViewModel() {

    private val _photos: MutableStateFlow<List<Photo>> = MutableStateFlow(emptyList())
    val photos: StateFlow<List<Photo>> = _photos.asStateFlow()

    private val _isSearchViewOpen = MutableLiveData<Boolean>()
    val isSearchViewOpen : LiveData<Boolean> =_isSearchViewOpen

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
        viewModelScope.launch {
            repository.getPhotos().collect() {
                val filteredPhotos = filterPhotos(it)
                _photos.value = filteredPhotos
            }
        }
    }

    private fun filterPhotos(photos: List<Photo>): List<Photo> {
        return photos.filter {
            it.description.contains(descriptionFilter, ignoreCase = true) &&
                    it.tags.containsAll(tagsFilter)
        }
    }

    fun closeSearchView() {
        _isSearchViewOpen.value = false
    }
}
