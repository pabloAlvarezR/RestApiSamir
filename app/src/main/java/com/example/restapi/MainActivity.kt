package com.example.restapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restapi.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    private lateinit var bind : ActivityMainBinding
    private lateinit var adapter: InmuebleAdapter
    private var currentPage = 0
    private var isLastPage = false
    private var isLoading = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        adapter = InmuebleAdapter(applicationContext, mutableListOf())
        bind.rvMain.adapter = adapter
        bind.rvMain.layoutManager = LinearLayoutManager(applicationContext)

        val inmuebleApiService = InmuebleApi.retrofit.create(InmuebleApi::class.java)

        loadInmuebles(inmuebleApiService)


        bind.rvMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()


                if (!isLoading && !isLastPage && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                    loadInmuebles(inmuebleApiService)
                }
            }
        })

//        bind.fabAnnadir.setOnClickListener{
//            val inmueble = Inmueble(
//                idInmueble = -1,
//                titulo = "Casa de campo",
//                precio = 100000.0f,
//                descripcion = "Hermosa casa de campo en un entorno natural",
//                metrosConstruidos = 150,
//                metrosUtiles = 120,
//                ubicacion = "Calle Principal 123",
//                zona = "Ejido Sur",
//                fechaPublicacion = "2022-02-02",
//                habitaciones = 3,
//                bannos = 2
//            )
//            val call = inmuebleApiService.altaInmueble(inmueble)
//            call.enqueue(object : Callback<Inmueble> {
//                override fun onResponse(call: Call<Inmueble>, response: Response<Inmueble>) {
//                    if (response.isSuccessful) {
//                        val inmuebleResponse = response.body()
//                        if (inmuebleResponse != null) {
//                            addInmueble(inmuebleResponse)
//                            Toast.makeText(applicationContext,response.toString(),Toast.LENGTH_SHORT).show()
//                        }
//                    } else {
//                        Toast.makeText(applicationContext,response.toString(),Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//                override fun onFailure(call: Call<Inmueble>, t: Throwable) {
//                    Log.d("INMUEBLE",t.toString())
//                }
//            })
//        }


    }

    fun addInmueble(inmueble: Inmueble) {
        Toast.makeText(this,"Inmueble añadido",Toast.LENGTH_SHORT).show()
        adapter.addInmueble(inmueble)
    }

    private fun loadInmuebles(inmuebleApiService: InmuebleApi) {
        isLoading = true
        val call = inmuebleApiService.getInmueble(currentPage, 4)
        call.enqueue(object : Callback<InmuebleResponse> {
            override fun onResponse(call: Call<InmuebleResponse>, response: Response<InmuebleResponse>) {
                if (response.isSuccessful) {
                    val inmuebles = response.body()?.content.orEmpty()
                    if (inmuebles.isNotEmpty()) {
                        if (currentPage == 0) {
                            adapter.setInmuebles(inmuebles.toMutableList())
                        } else {
                            adapter.addInmuebles(inmuebles.toMutableList())
                        }
                        currentPage++
                    } else {
                        isLastPage = true
                    }
                }
                isLoading = false
            }

            override fun onFailure(call: Call<InmuebleResponse>, t: Throwable) {
                isLoading = false
                Log.d("INMUEBLE", t.toString())
                Toast.makeText(applicationContext, "Error al cargar inmuebles", Toast.LENGTH_SHORT).show()
            }
        })
    }
}