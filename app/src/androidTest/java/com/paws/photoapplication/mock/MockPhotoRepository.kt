package com.paws.photoapplication.mock

import com.paws.photoapplication.data.model.Photo
import com.paws.photoapplication.data.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class MockPhotoRepository : PhotoRepository {
    private val _photos: MutableStateFlow<List<Photo>> = MutableStateFlow(emptyList())

    override fun getPhotos(): Flow<List<Photo>> {
        return _photos
    }

    override fun add(photo: Photo) {
        _photos.value += photo
    }
}
