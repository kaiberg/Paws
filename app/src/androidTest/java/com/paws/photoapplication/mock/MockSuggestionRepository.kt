package com.paws.photoapplication.mock

import com.paws.photoapplication.data.repository.SuggestionRepository

class MockSuggestionRepository : SuggestionRepository {
    override suspend fun getSuggestions(): List<String> {
        return emptyList()
    }

    override suspend fun add(suggestion: String) {
    }
}