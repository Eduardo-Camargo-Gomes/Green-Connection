package com.example.green_connect

import androidx.annotation.DrawableRes

// Classe de dados Tip que representa uma dica a ser exibida na tela introdutória
data class Tip(
    val title: String,
    val subtitle: String,
    @DrawableRes val logo: Int,
    @DrawableRes val background: Int
)
