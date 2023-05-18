package com.example.livros.views


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.livros.models.Livro
import com.example.livros.viewmodels.LivrosViewModel
import com.example.livros.R


@Composable
fun LivroScreen(
    navController: NavController,
    livrosViewModel: LivrosViewModel
) {

    val uiState by livrosViewModel.livroScreenUiState.collectAsState()

    livroList(
        livros = uiState.allLivros,
        onFavoritedChange = { livro, isFavorited ->
            livrosViewModel.onLivroIsFavoritedChange(livro, isFavorited)
        },
        onEditLivro = { livrosViewModel.editLivro(it, navController) },
        onDeleteLivro = { livrosViewModel.onDeleteLivro(it) }
    )
}

@Composable
fun livroList(
    livros: List<Livro>,
    onFavoritedChange: (Livro, Boolean) -> Unit,
    onEditLivro: (Livro) -> Unit,
    onDeleteLivro: (Livro) -> Unit

) {
    LazyColumn() {
        items(livros) { livro ->
            LivroEntry(
                livro = livro,
                onFavoritedChange = { onFavoritedChange(livro, it) },
                onEditLivro = { onEditLivro(livro) },
                onDeleteLivro = { onDeleteLivro(livro) })
        }
    }
}

@Composable
fun LivroEntry(
    livro: Livro,
    onFavoritedChange: (Boolean) -> Unit,
    onEditLivro: () -> Unit,
    onDeleteLivro: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onEditLivro() }, elevation = 4.dp,
        backgroundColor = Color(0xFFE8F1F2)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = livro.titulo,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            )

            IconToggleButton(
                checked = livro.isFavorited,
                onCheckedChange = { onFavoritedChange(!livro.isFavorited) }
            ) {
                if (livro.isFavorited) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_favorite_24),
                        contentDescription = "Favorited"
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_favorite_border_24),
                        contentDescription = "Not Favorited"
                    )
                }
            }

            var showDeleteConfirmationDialog by remember { mutableStateOf(false) }

            IconButton(
                onClick = { showDeleteConfirmationDialog = true },
                modifier = Modifier
                    .background(color = Color.Transparent)
                    .clip(RoundedCornerShape(8.dp)),
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_delete_24),
                        contentDescription = "Delete Livro"
                    )
                }
            )

            if (showDeleteConfirmationDialog) {
                AlertDialog(
                    onDismissRequest = { showDeleteConfirmationDialog = false },
                    title = {
                        Text(
                            text = "Confirmar exclusão",
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    },
                    text = {
                        Text(
                            text = "Tem certeza de que deseja excluir este livro?",
                            fontSize = 14.sp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                onDeleteLivro()
                                showDeleteConfirmationDialog = false
                            },
                            colors = ButtonDefaults.textButtonColors(contentColor = Color.Black)
                        ) {
                            Text(
                                text = "SIM"
                            )
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showDeleteConfirmationDialog = false
                            },
                            colors = ButtonDefaults.textButtonColors(contentColor = Color.Black)
                        ) {
                            Text(
                                text = "NÃO"
                            )
                        }
                    }
                )
            }
        }
    }
}

