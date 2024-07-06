package com.example.green_connect

import androidx.annotation.DrawableRes

// Classe de dados Tip que representa uma dica a ser exibida na tela introdutória
data class Tip(
    val title: String,       // Título da dica
    val subtitle: String,    // Subtítulo ou descrição da dica
    @DrawableRes val logo: Int,        // Referência ao recurso drawable do logo associado à dica
    @DrawableRes val background: Int   // Referência ao recurso drawable do fundo associado à dica
)
