package com.paws.photoapplication

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.paws.photoapplication.ui.MainActivity

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Rule
    @JvmField
    val activity = ActivityTestRule(MainActivity::class.java)
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.paws.photoapplication", appContext.packageName)
    }

    @Test
    fun `addPhoto_button_leads_to_capture_screen`() {
        onView(withId(R.id.addPhotoButton)).perform(click())
        onView(withId(R.id.image_capture_button)).check(matches(isDisplayed()))
    }

    @Test
    fun `adding_photo_from_gallery_leads_to_photoCreate_fragment`() {
        onView(withId(R.id.addPhotoButton)).perform(click())
        onView(withId(R.id.image_capture_button)).perform(click())
        onView(withId(R.id.selectedImage)).check(matches(isDisplayed()))
    }
}