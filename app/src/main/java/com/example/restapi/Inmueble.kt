package com.example.restapi

data class Inmueble(
    val idInmueble: Long,
    val titulo: String,
    val precio: Float,
    val descripcion: String,
    val metrosConstruidos: Int,
    val metrosUtiles: Int,
    val ubicacion: String,
    val zona: String,
    val fechaPublicacion: String,
    val habitaciones: Int,
    val bannos: Int
)


data class InmuebleResponse(val content:List<Inmueble>)
