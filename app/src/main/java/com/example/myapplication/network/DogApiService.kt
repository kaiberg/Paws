package com.example.myapplication.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

const val BASE_URL = "https://dog.ceo/api/"
/*
    {breed}/images/random/{count?} -- get count random images of breed
*/
const val BY_BREED = "breed/"

/*
    /list/all -- get all breeds
    /image/random/{count?} -- get count random images of dogs
*/
const val STANDARD = "breeds/"

val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

data class BreedsResponse(val message: Array<Array<String>>, val status: String)
data class PhotosResponse(val message: Array<String>, val status: String)


interface DogApiService {
    @GET("breeds/image/random/{count}")
    suspend fun GetPhotos(@Path("count") count: Int = 1) : PhotosResponse

    @GET("breed/{breed}/images/random/{count}")
    suspend fun GetPhotosByBreed(@Path("count") count: Int = 1, @Path("breed") breed: String) : PhotosResponse

    @GET("breeds/list/all")
    suspend fun GetBreeds() : BreedsResponse
}

object DogApi {
    val retrofitService: DogApiService by lazy { retrofit.create(DogApiService::class.java) }
}