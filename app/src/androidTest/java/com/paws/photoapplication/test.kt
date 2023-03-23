package com.paws.photoapplication

import com.paws.photoapplication.data.model.Photo
import com.paws.photoapplication.ui.photoCreate.PhotoCreateViewModel
import com.paws.photoapplication.ui.photoGallery.PhotoGalleryViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class PhotoGalleryViewModelTest1 {
    private lateinit var repository: MockPhotoRepository
    private lateinit var galleryViewModel: PhotoGalleryViewModel
    private lateinit var createViewModel: PhotoCreateViewModel


    @Before
    fun setUp() {
        repository = MockPhotoRepository()
        galleryViewModel = PhotoGalleryViewModel(repository)
        createViewModel = PhotoCreateViewModel(repository)
    }

    @Test
    fun adding_a_photo_to_repository_updates_viewmodel() = runTest {
        val photo = Photo("path", listOf("tag1","tag2","tag3"),"description")
        repository.add(photo)

        delay(1000)

        assertEquals(photo, galleryViewModel.photos.value.last())
    }

}
