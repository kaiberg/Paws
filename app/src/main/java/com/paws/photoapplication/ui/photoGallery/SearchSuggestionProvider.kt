package com.paws.photoapplication.ui.photoGallery

import android.content.SearchRecentSuggestionsProvider

class SearchDescriptionSuggestionProvider : SearchRecentSuggestionsProvider() {
    init {
        setupSuggestions(AUTHORITY, MODE)
    }

    companion object {
        const val AUTHORITY = "com.paws.SearchDescriptionSuggestionProvider"
        const val MODE: Int = DATABASE_MODE_QUERIES
    }
}
