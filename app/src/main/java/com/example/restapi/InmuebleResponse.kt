package com.example.restapi

data class InmuebleResponse(
    var titulo:String, var precio:Float, var descripcion: String, var metrosConstruidos: Long, var metrosUtiles: Double,
    var ubicacion: String, var zona: String, var fechaPublicacion: String, var habitaciones: Long,
    var bannos: Long, var idInmueble: Long)
