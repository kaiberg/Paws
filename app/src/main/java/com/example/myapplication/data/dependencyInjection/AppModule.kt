package com.example.myapplication.data.dependencyInjection

import com.example.myapplication.data.network.BASE_URL
import com.example.myapplication.data.network.DogApiService
import com.example.myapplication.data.repository.DogCEOPhotoRepository
import com.example.myapplication.data.repository.PhotoRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePhotoRepository(dogApi: DogApiService) : PhotoRepository {
        return DogCEOPhotoRepository(dogApi)
    }

    @Provides
    @Singleton
    fun provideRetroFit() : Retrofit {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build()

        return retrofit
    }

    @Provides
    @Singleton
    fun provideDogApi(retrofit: Retrofit) : DogApiService =
        retrofit.create(DogApiService::class.java)
}
