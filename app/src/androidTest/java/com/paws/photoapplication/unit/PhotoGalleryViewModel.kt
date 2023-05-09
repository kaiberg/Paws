package com.paws.photoapplication.unit

import com.paws.photoapplication.data.model.Photo
import com.paws.photoapplication.mock.MockPhotoRepository
import com.paws.photoapplication.mock.MockSuggestionRepository
import com.paws.photoapplication.ui.photoGallery.PhotoGalleryViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class PhotoGalleryViewModel {
    private lateinit var mockPhotoRepository: MockPhotoRepository
    private lateinit var mockSuggestionRepository: MockSuggestionRepository
    private lateinit var viewModel: PhotoGalleryViewModel
    private lateinit var photo1: Photo
    private lateinit var photo2: Photo
    private lateinit var photoList: ArrayList<Photo>


    @Before
    fun setUp() {
        mockPhotoRepository = MockPhotoRepository()
        mockSuggestionRepository = MockSuggestionRepository()
        viewModel = PhotoGalleryViewModel(mockPhotoRepository,mockSuggestionRepository)
        photo1 = Photo("test_path1", listOf("tag1"), "test_description1")
        photo2 = Photo("test_path2", listOf("tag2"), "test_description2")

        mockPhotoRepository.add(photo1)
        mockPhotoRepository.add(photo2)

        photoList = ArrayList()
        photoList.add(photo1)
        photoList.add(photo2)
    }

    @Test
    fun adding_a_photo_to_repository_updates_viewmodel() = runTest {
        val photo = Photo("path", listOf("tag1","tag2","tag3"),"description")
        mockPhotoRepository.add(photo)

        delay(1000)

        assertEquals(photo, viewModel.photos.value.last())
    }

    @Test
    fun search_description() = runTest {
        viewModel.setDescriptionFilter(photo1.description)
        viewModel.setTagsFilter(listOf())

        viewModel.search()
        delay(100)

        assertEquals(listOf(photo1), viewModel.photos.value)
    }

    @Test
    fun search_tags() = runBlocking {
        viewModel.setDescriptionFilter("")
        viewModel.setTagsFilter(photo2.tags)
        viewModel.search()

        delay(1000)

        assertEquals(listOf(photo2), viewModel.photos.value)
    }

    @Test
    fun searching_again_gets_the_original_list_of_images() = runBlocking {
        viewModel.setDescriptionFilter(photo2.description)
        viewModel.setTagsFilter(photo2.tags)
        viewModel.search()

        delay(1000)

        assertEquals(listOf(photo2), viewModel.photos.value)

        viewModel.setDescriptionFilter("")
        viewModel.setTagsFilter(emptyList())
        viewModel.search()

        delay(1000)

        assertEquals(listOf(photo1, photo2), viewModel.photos.value)
    }
}
