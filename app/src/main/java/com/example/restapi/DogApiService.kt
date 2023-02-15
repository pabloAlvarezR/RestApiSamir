package com.example.restapi

import okhttp3.Call
import retrofit2.http.GET

interface DogApiService {
    @GET("breeds/image/random")
    fun getRandomDogImage(): Call
}