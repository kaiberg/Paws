package com.example.myapplication.ui.viewmodels

import android.provider.ContactsContract.Contacts.Photo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.ui.DataManager
import com.example.myapplication.ui.photo
import kotlinx.coroutines.launch

class PhotoGalleryViewModel : ViewModel() {
    var photos: MutableLiveData<List<photo>> = MutableLiveData()

    init {
        viewModelScope.launch {        DataManager.SeedData()
        }

        DataManager.onChangeListener = {search()}
    }

    fun search(descriptionFilter : String = "",
               tagsFilter: Array<String> = emptyArray()) {
        if(DataManager.photos.isEmpty())
            return
        val result = DataManager.photos.filter {
            it.description.uppercase().contains(descriptionFilter.uppercase()) &&
                    it.keyWords.containsAll(tagsFilter.toList())
        }

        photos.value = result
    }
}