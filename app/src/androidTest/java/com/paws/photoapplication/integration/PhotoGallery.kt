package com.paws.photoapplication.integration

import android.widget.ImageView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.paws.photoapplication.R
import com.paws.photoapplication.data.model.Photo
import com.paws.photoapplication.data.repository.PhotoRepository
import com.paws.photoapplication.di.launchFragmentInHiltContainer
import com.paws.photoapplication.mock.MockPhotoRepository
import com.paws.photoapplication.ui.photoGallery.PhotoGalleryFragment
import com.paws.photoapplication.ui.photoGallery.PhotoGalleryFragmentDirections
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.hamcrest.Matchers.allOf

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import javax.inject.Inject

/**
 * Instrumented test, which will execute on an Android device.
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class PhotoGallery {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: PhotoRepository
    lateinit var mockNavController: NavController

    val photo = Photo(path = "fakePath", tags = listOf("tag1", "2", "3", "4", "5"), description = "fake description")

    @Before
    fun setup() {
        hiltRule.inject()
        repository.add(photo)

        mockNavController = mock(NavController::class.java)
        launchFragmentInHiltContainer<PhotoGalleryFragment> {
            Navigation.setViewNavController(requireView(), mockNavController)
        }
    }

    @Test
    fun `verify_injection_and_context`() {
        assert(repository is MockPhotoRepository)
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.paws.photoapplication", appContext.packageName)
    }

    @Test
    fun `all_photos_in_repository_are_shown_in_view`() = runTest {
        onView(withId(R.id.photoDisplay)).check(matches(
            hasDescendant(
                allOf(
                    withId(R.id.imv_photo),
                    withId(R.id.txv_description),
                    withId(R.id.cg_keywords)
                ),

            )
        ))

        onView(withId(R.id.photoDisplay)).check(matches(
            hasDescendant(
                allOf(
                    withText(photo.description),
                    withId(R.id.imv_photo),
                    *photo.tags.map { withText(it) }.toTypedArray()
                )
            )
        ))
    }


    @Test
    fun `addPhoto_button_leads_to_capture_screen`()  {
        onView(withId(R.id.addPhotoButton)).perform(click())
        verify(mockNavController).navigate(PhotoGalleryFragmentDirections.actionPhotoGalleryFragmentToPhotoCapture())
        assertEquals(R.id.photoCapture, mockNavController.currentDestination?.id)
    }


    @Test
    fun `adding_photo_from_gallery_leads_to_photoCreate_fragment`() {
        onView(withText("fake description")).check(matches(isDisplayed()))
        onView(withId(R.id.addPhotoButton)).perform(click())
        onView(withId(R.id.image_capture_button)).perform(click())
        onView(withId(R.id.selectedImage)).check(matches(isDisplayed()))
    }
}