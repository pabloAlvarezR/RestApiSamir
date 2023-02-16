package com.example.restapi


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DogApiService {
    @GET("breeds/image/random")
    fun getRandomDogImage(@Query("count") count: Int): Call<DogApiResponse>
}