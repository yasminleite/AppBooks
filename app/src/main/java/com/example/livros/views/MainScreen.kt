package com.example.livros.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.livros.models.Livro
import com.example.livros.viewmodels.LivrosViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    livrosViewModel: LivrosViewModel = viewModel()
) {
    val navController = rememberNavController()
    val uiState by livrosViewModel.mainScreenUiState.collectAsState()
    var isIconClicked by remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color(0xFF247BA0)
            ) {
                Text(
                    text = uiState.screenTitulo,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        floatingActionButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FloatingActionButton(
                    onClick = {
                        isIconClicked = !isIconClicked
                        livrosViewModel.toggleShowLivros()
                    }
                ) {
                    Icon(
                        painter = painterResource(id = if (isIconClicked) uiState.quaIcon else uiState.segIcon),
                        contentDescription = null
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                FloatingActionButton(
                    onClick = {
                    livrosViewModel.navigate(navController)
                }
                ) {
                    Icon(
                        painter = painterResource(id = uiState.fabIcon),
                        contentDescription = null)
                }
            }
        }
    ) {
        NavHost(navController = navController, startDestination = "livro_list") {
            composable("livro_list") {
                LivroScreen(navController = navController, livrosViewModel = livrosViewModel)
            }
            composable("insert_edit_livro") {
                InsertEditLivroScreen(
                    navController = navController,
                    livrosViewModel = livrosViewModel
                )
            }
        }
    }
}
