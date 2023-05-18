package com.example.livros.viewmodels

data class InsertEditScreenUiState(
    val livroTitulo: String = "",
    val livroAutor: String = "",
    val isFavorited: Boolean = false,
)

