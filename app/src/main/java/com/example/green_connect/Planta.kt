package com.example.green_connect

data class Planta(
    val nome: String = "",
    val umidadeMaxima: Int = 0,
    val umidadeMinima: Int = 0,
    val temperaturaMaxima: Int = 0,
    val temperaturaMinima: Int = 0,
    val quantidadeAgua: Int = 0
)
