package com.paws.photoapplication.ui.optionsDialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paws.photoapplication.data.model.Option
import com.paws.photoapplication.data.repository.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OptionsViewModel @Inject constructor(private val photoRepository: PhotoRepository): ViewModel() {
    private val _options = MutableLiveData<List<Option>>()
    var options : LiveData<List<Option>> = MutableLiveData()
        get() = _options
    init{
        viewModelScope.launch {
            photoRepository.getPhotos().collect { _options.value = it.flatMap { it.tags }.distinct().map { Option(it, false) } }
        }
    }
}