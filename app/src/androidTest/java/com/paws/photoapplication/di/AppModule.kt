package com.paws.photoapplication.di

import com.paws.photoapplication.mock.MockPhotoRepository
import com.paws.photoapplication.data.di.AppModule
import com.paws.photoapplication.data.repository.PhotoRepository
import com.paws.photoapplication.data.repository.SuggestionRepository
import com.paws.photoapplication.mock.MockSuggestionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
object FakeAppModule {

    @Singleton
    @Provides
    fun providePhotoRepository() : PhotoRepository {
        return MockPhotoRepository()
    }

    @Singleton
    @Provides
    fun provideSuggestionRepository() : SuggestionRepository {
        return MockSuggestionRepository()
    }
}
