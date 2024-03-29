package com.paws.photoapplication.data.network

import com.squareup.moshi.Json
import retrofit2.http.GET
import retrofit2.http.Path

const val BASE_URL = "https://dog.ceo/api/"
/*
    {breed}/images/random/{count?} -- get count random images of breed
*/
const val BY_BREED = "breed"

/*
    /list/all -- get all breeds
    /image/random/{count?} -- get count random images of dogs
*/
const val STANDARD = "breeds"

const val RANDOM = "random"

data class BreedsResponse(
    @Json(name = "message")
    val message: Array<Array<String>>,
    @Json(name = "status")
    val status: String
    )
data class PhotosResponse(
    @Json(name = "message")
    val message: Array<String>,
    @Json(name = "status")
    val status: String
    )


interface DogApiService {
    @GET("${STANDARD}/image/${RANDOM}/{count}")
    suspend fun GetPhotos(@Path("count") count: Int = 1) : PhotosResponse

    @GET("${BY_BREED}/{breed}/images/${RANDOM}/{count}")
    suspend fun GetPhotosByBreed(@Path("count") count: Int = 1, @Path("breed") breed: String) : PhotosResponse

    @GET("breeds/list/all")
    suspend fun GetBreeds() : BreedsResponse
}

