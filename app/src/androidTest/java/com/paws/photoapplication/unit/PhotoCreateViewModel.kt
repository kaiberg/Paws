package com.paws.photoapplication.unit

import com.paws.photoapplication.data.model.Photo
import com.paws.photoapplication.mock.MockPhotoRepository
import com.paws.photoapplication.mock.MockSuggestionRepository
import com.paws.photoapplication.ui.photoCreate.PhotoCreateViewModel
import com.paws.photoapplication.ui.photoGallery.PhotoGalleryViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.delay
import org.junit.Assert.*


class PhotoCreateViewModel {
    private lateinit var photoRepository: MockPhotoRepository
    private lateinit var suggestionRepository : MockSuggestionRepository
    private lateinit var galleryViewModel: PhotoGalleryViewModel
    private lateinit var createViewModel: PhotoCreateViewModel


    @Before
    fun setUp() {
        photoRepository = MockPhotoRepository()
        suggestionRepository = MockSuggestionRepository()
        galleryViewModel = PhotoGalleryViewModel(photoRepository,suggestionRepository)
        createViewModel = PhotoCreateViewModel(photoRepository)

        val photo1 = Photo("test_path1", listOf("tag1"), "test_description1")
        val photo2 = Photo("test_path2", listOf("tag2"), "test_description2")

        photoRepository.add(photo1)
        photoRepository.add(photo2)
    }


    @Test
    fun create_photo_updates_gallery() = runTest{
        val photo = Photo("path", listOf("tag1","tag2","tag3"),"description")
        createViewModel.tags = ArrayList(photo.tags)
        createViewModel.photoURL = photo.path
        createViewModel.description = photo.description

        val originalCount = galleryViewModel.photos.value.size
        createViewModel.createPhoto()
        delay(1000)
        assertEquals(originalCount+1, galleryViewModel.photos.value.size)
        assertEquals(photo, galleryViewModel.photos.value.last())
    }
}
