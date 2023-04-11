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

    private var _descriptionFilter: MutableLiveData<String> = MutableLiveData("")
    var descriptionFilter: LiveData<String> = _descriptionFilter
    private var _tagsFilter: MutableLiveData<List<String>> = MutableLiveData(emptyList())
    var tagsFilter: LiveData<List<String>> = _tagsFilter

    init {
        search()
    }

    fun setDescriptionFilter(filter: String) {
        _descriptionFilter.value = filter
    }

    fun setTagsFilter(filter: List<String>) {
        _tagsFilter.value = filter
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
            it.description.contains(_descriptionFilter.value!!, ignoreCase = true) &&
                    it.tags.containsAll(_tagsFilter.value!!)
        }
    }


}
