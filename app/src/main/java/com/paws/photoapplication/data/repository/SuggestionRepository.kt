package com.paws.photoapplication.data.repository

import com.paws.photoapplication.data.model.Suggestion
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
interface SuggestionRepository {
    suspend fun getSuggestions() : List<String>
    suspend fun add(suggestion: String)
}

class SQLiteSuggestionRepository @Inject constructor(private val suggestionDao: SuggestionDao) : SuggestionRepository {
    override suspend fun getSuggestions(): List<String> {
        return suggestionDao.getRecentSuggestions()
    }

    override suspend fun add(suggestion: String) {
        suggestionDao.insertSuggestion(Suggestion(0, suggestion))
    }

}