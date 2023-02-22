package com.example.restapi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class InmuebleAdapter(val context: Context,private var inmuebles:MutableList<Inmueble>)  :RecyclerView.Adapter<InmuebleAdapter.InmuebleHolder>() {
    private var fInmuebles = inmuebles
    class InmuebleHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvTitulo : TextView = itemView.findViewById(R.id.tituloTextView)
        val tvPrecio : TextView = itemView.findViewById(R.id.precioTextView)
        val tvDescripcion : TextView = itemView.findViewById(R.id.descripcionTextView)
        val tvMetros : TextView = itemView.findViewById(R.id.metrosTextView)
//        val btnBorrar : Button = itemView.findViewById(R.id.borrar_button)
//        val btnEditar : Button = itemView.findViewById(R.id.editar_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InmuebleHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.esqueleto_perro, parent, false)
        return InmuebleHolder(view)
    }

    override fun getItemCount(): Int {
        return fInmuebles.size
    }

    override fun onBindViewHolder(holder: InmuebleHolder, position: Int) {
        val inmueble = fInmuebles[position]
        val inmuebleApiService = InmuebleApi.retrofit.create(InmuebleApi::class.java)
        holder.tvTitulo.text = inmueble.titulo
        holder.tvPrecio.text = String.format("%.2f €",inmueble.precio)
        holder.tvDescripcion.text = inmueble.descripcion
        holder.tvMetros.text = inmueble.metrosUtiles.toString()
//        holder.btnBorrar.setOnClickListener{
//            val call = inmuebleApiService.deleteInmueble(inmueble.idInmueble.toInt())
//            call.enqueue(object : Callback<Void> {
//                override fun onResponse(call: Call<Void>, response: Response<Void>) {
//                    if (response.isSuccessful) {
//                        Toast.makeText(context,"Inmueble eliminado",Toast.LENGTH_SHORT).show()
//                        fInmuebles.removeAt(holder.adapterPosition)
//                        notifyItemRemoved(holder.adapterPosition)
//                    }
//                }
//
//                override fun onFailure(call: Call<Void>, t: Throwable) {
//                    Log.d("INMUEBLE",t.toString())
//                }
//            })
//
//        }

//        holder.btnEditar.setOnClickListener {
//            val inmueble2 = Inmueble(
//                idInmueble = inmueble.idInmueble,
//                titulo = "Mansion de Carolina Herrera",
//                precio = 100000.0f,
//                descripcion = "Mansion",
//                metrosConstruidos = 150,
//                metrosUtiles = 120,
//                ubicacion = "Calle Principal 123",
//                zona = "Ejido Sur",
//                fechaPublicacion = "2022-02-02",
//                habitaciones = 3,
//                bannos = 2
//            )
//            val call = inmuebleApiService.editarInmueble(inmueble2.idInmueble.toInt(),inmueble2)
//            call.enqueue(object : Callback<Inmueble> {
//                override fun onResponse(call: Call<Inmueble>, response: Response<Inmueble>) {
//                    if (response.isSuccessful) {
//                        val inmuebleResponse = response.body()
//                        if (inmuebleResponse != null) {
//                            holder.tvTitulo.text = inmuebleResponse.titulo
//                            holder.tvPrecio.text = String.format("%.2f €",inmuebleResponse.precio)
//                            holder.tvDescripcion.text = inmuebleResponse.descripcion
//                            holder.tvMetros.text = inmuebleResponse.metrosUtiles.toString()
//
//
//
//                        }
//                    } else {
//                        Log.d("INMUEBLE", response.toString())
//                    }
//                }
//
//                override fun onFailure(call: Call<Inmueble>, t: Throwable) {
//                    Log.d("INMUEBLE",t.toString())
//                }
//            })
//        }
    }

    fun filterInmuebles(query: String) {
        fInmuebles = inmuebles.filter { inmueble ->
            inmueble.titulo.contains(query, true)
        } as MutableList<Inmueble>
        notifyDataSetChanged()
    }

    fun setInmuebles(newInmuebles: MutableList<Inmueble>) {
        fInmuebles = newInmuebles
        notifyDataSetChanged()
    }

    fun addInmueble(inmueble: Inmueble) {
        notifyDataSetChanged()
    }

    fun addInmuebles(newInmuebles: MutableList<Inmueble>) {
        fInmuebles.addAll(newInmuebles)
        notifyDataSetChanged()
    }

}