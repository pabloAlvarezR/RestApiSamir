package com.example.restapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
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

        dogApiService.getRandomDogImage().enqueue(object : Callback<DogApiResponse> {
            fun onResponse(call: Call<DogApiResponse>, response: Response<DogApiResponse>) {
                if (response.isSuccessful) {
                    images.add(response.body()?.message ?: "")
                    adapter.notifyItemInserted(images.size - 1)
                }
            }

            fun onFailure(call: Call<DogApiResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
