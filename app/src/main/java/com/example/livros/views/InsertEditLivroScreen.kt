package com.example.livros.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.livros.viewmodels.LivrosViewModel
import com.example.livros.R

@Composable
fun InsertEditLivroScreen(
    navController: NavController,
    livrosViewModel: LivrosViewModel
) {
    val uiState by livrosViewModel.insertEditScreenUiState.collectAsState()
    InsertEditForm(
        titulo = uiState.livroTitulo,
        autor = uiState.livroAutor,
        onTituloChange = { livrosViewModel.onLivroTituloChange(it) },
        onAutorChange = { livrosViewModel.onLivroAutorChange(it) },
    )
}

@Composable
fun InsertEditForm(
    titulo: String,
    autor: String,
    onTituloChange: (String) -> Unit,
    onAutorChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            OutlinedTextField(
                label = { Text(text = "Título do livro") },
                value = titulo, onValueChange = onTituloChange
            )
            Column(horizontalAlignment = Alignment.Start) {
                OutlinedTextField(
                    label = { Text(text = "Autor do livro") },
                    value = autor, onValueChange = onAutorChange
                )
            }
        }
    }
}

