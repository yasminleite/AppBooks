package com.example.livros.viewmodels

import com.example.livros.models.Livro

data class LivroScreenUiState(
    val allLivros: List<Livro> = listOf(
        Livro("Uma canção de amor e ódio", "Vinícius Grossos"),
        Livro("É assim que acaba", "Colleen Hoover"),
        Livro("Antes que eu vá", "Lauren Oliver", isFavorited = true),
        Livro("Minha vida fora de série", "Paula Pimenta"),
        Livro("Eleanor & Park", "Rainbow Rowell", isFavorited = true),
        Livro("A geografia de nós dois", "Jennifer E. Smith")
    ),
)
