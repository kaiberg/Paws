package com.paws.photoapplication.ui.photoGallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paws.photoapplication.data.model.Photo
import com.paws.photoapplication.data.repository.PhotoRepository
import com.paws.photoapplication.data.repository.SuggestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PhotoGalleryViewModel @Inject constructor(
    private val photoRepository: PhotoRepository,
    private val suggestionRepository: SuggestionRepository
) : ViewModel() {

    private val _photos: MutableStateFlow<List<Photo>> = MutableStateFlow(emptyList())
    val photos: StateFlow<List<Photo>> = _photos.asStateFlow()

    private var _descriptionFilter: MutableLiveData<String> = MutableLiveData("")
    var descriptionFilter: LiveData<String> = _descriptionFilter

    private var _tagsFilter: MutableLiveData<List<String>> = MutableLiveData(emptyList())
    var tagsFilter: LiveData<List<String>> = _tagsFilter

    private var _suggestions: MutableLiveData<List<String>> = MutableLiveData(emptyList())
    var suggestions: LiveData<List<String>> = _suggestions

    init {
        viewModelScope.launch { _suggestions.value = suggestionRepository.getSuggestions() }
        search()
    }

    fun setDescriptionFilter(filter: String) {
        _descriptionFilter.postValue(filter)

        if (filter.isNotEmpty())
            viewModelScope.launch { suggestionRepository.add(filter) }
    }

    fun setTagsFilter(filter: List<String>) {
        _tagsFilter.postValue(filter)
    }

    fun search() {
        viewModelScope.launch {
            photoRepository.getPhotos().collect() {
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
