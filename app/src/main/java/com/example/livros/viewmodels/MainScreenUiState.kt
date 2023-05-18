package com.example.livros.viewmodels

import androidx.annotation.DrawableRes
import com.example.livros.R

data class MainScreenUiState(
    val screenTitulo: String = "LISTA DE LIVROS",
    @DrawableRes val fabIcon: Int = R.drawable.baseline_add_24,
    @DrawableRes val segIcon: Int = R.drawable.baseline_favorite_24,
    @DrawableRes val terIcon: Int = R.drawable.baseline_favorite_border_24,
    @DrawableRes val quaIcon: Int = R.drawable.baseline_book_24,
)
