package com.example.restapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    private val dogApiService: DogApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DogApiService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val images = mutableListOf<String>()
        val adapter = DogImageAdapter(this, images)
        recyclerView.adapter = adapter

        val NUM_IMAGES = 20 // Número de imágenes que deseas mostrar
        var isLoading = false

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisibleItemPosition = (recyclerView.layoutManager as GridLayoutManager).findLastVisibleItemPosition()
                val totalItemCount = recyclerView.adapter?.itemCount ?: 0
                if (lastVisibleItemPosition == totalItemCount - 1 && !isLoading) {
                    isLoading = true
                    dogApiService.getRandomDogImage(NUM_IMAGES).enqueue(object : Callback<DogApiResponse> {
                        override fun onResponse(call: Call<DogApiResponse>, response: Response<DogApiResponse>) {
                            if (response.isSuccessful) {
                                val newImages: List<String> = response.body()?.message?.let { listOf(it) } ?: emptyList()
                                images.addAll(newImages)
                                adapter.notifyItemRangeInserted(totalItemCount, newImages.size)
                                isLoading = false
                            }
                        }

                        override fun onFailure(call: Call<DogApiResponse>, t: Throwable) {
                            Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                            isLoading = false
                        }
                    })
                }
            }
        })

        dogApiService.getRandomDogImage(NUM_IMAGES).enqueue(object : Callback<DogApiResponse> {
            override fun onResponse(call: Call<DogApiResponse>, response: Response<DogApiResponse>) {
                if (response.isSuccessful) {
                    val newImages: List<String> = response.body()?.message?.let { listOf(it) } ?: emptyList()
                    images.addAll(newImages)
                    adapter.notifyItemRangeInserted(0, newImages.size)
                }
            }

            override fun onFailure(call: Call<DogApiResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })

    }

}