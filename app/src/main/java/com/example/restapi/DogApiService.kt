package com.example.restapi


import retrofit2.Call
import retrofit2.http.GET

interface DogApiService {
    @GET("breeds/image/random")
    fun getRandomDogImage(): Call<DogApiResponse>
}