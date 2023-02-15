package com.example.restapi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class DogImageAdapter(private val context: Context, private val images: List<String>) : RecyclerView.Adapter<DogImageAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imgPerro)
    }

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.esqueleto_perro, parent, false)
        return ViewHolder(view)
    }

    fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
            .load(images[position])
            .into(holder.imageView)
    }

    fun getItemCount() = images.size
}
