package com.paws.photoapplication.data.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.paws.photoapplication.data.model.Suggestion
import com.paws.photoapplication.data.network.BASE_URL
import com.paws.photoapplication.data.network.DogApiService
import com.paws.photoapplication.data.repository.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return AppDatabase.getInstance(appContext)
    }

    @Provides
    @Singleton
    fun provideSuggestionDao(appDatabase: AppDatabase): SuggestionDao {
        return appDatabase.suggestionDao()
    }

    @Singleton
    @Provides
    fun provideSuggestionRepository(suggestionDao: SuggestionDao) : SuggestionRepository {
        return SQLiteSuggestionRepository(suggestionDao)
    }
}

@Database(entities = [Suggestion::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun suggestionDao(): SuggestionDao

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build().also { instance = it }
            }
        }
    }
}

