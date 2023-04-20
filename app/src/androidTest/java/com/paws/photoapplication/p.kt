package com.paws.photoapplication

import com.paws.photoapplication.data.repository.PhotoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {
    @Provides
    fun providePhotoRepository(): PhotoRepository {
        return MockPhotoRepository()
    }
}