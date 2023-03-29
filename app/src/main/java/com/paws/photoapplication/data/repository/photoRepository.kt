package com.paws.photoapplication.data.repository

import android.util.Log.d
import com.paws.photoapplication.data.model.Photo
import com.paws.photoapplication.data.network.DogApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random


@Singleton
interface PhotoRepository {
    fun getPhotos() : Flow<List<Photo>>
    fun add(photo : Photo)
}

class  NolekPhotoRepository @Inject constructor(): PhotoRepository {
    override fun getPhotos(): Flow<List<Photo>> {
        TODO("Not yet implemented")
    }

    override fun add(photo: Photo) {
        TODO("Not yet implemented")
    }

}

@Singleton
class  DogCEOPhotoRepository @Inject constructor(val dogApi: DogApiService): PhotoRepository {
    private val _photos: MutableStateFlow<List<Photo>> = MutableStateFlow(emptyList())
    val tags: List<String> = listOf("Udstyr", "DMOE21", "AB589", "face", "person", "1banan9", "crai", "sdfpij", "45398", "adiu453fgd", "abab", "legitrc")

    init {
        CoroutineScope(Dispatchers.IO).launch {
            SeedData()
        }
    }


    private suspend fun SeedData() {
        val sampleText = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."

        val response = dogApi.GetPhotos(50)
        d("http", "$response")
        val photoSet = response.message.map {
            val start = Random.nextInt(0, tags.size)
            val end = Random.nextInt(start, tags.size)

            val sstart = Random.nextInt(0,256)
            val send = Random.nextInt(sstart, Math.min(sstart+256, sampleText.length))
            Photo(it, tags.subList(start,end),sampleText.substring(sstart,send))
        }

        d("photoset set to", "$photoSet")
        _photos.value = photoSet
    }
    override fun getPhotos(): Flow<List<Photo>> {
        return _photos
    }

    override fun add(photo: Photo) {
        _photos.value += photo
    }
}
