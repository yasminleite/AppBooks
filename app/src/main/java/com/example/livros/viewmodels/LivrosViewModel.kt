package com.example.livros.viewmodels

import android.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.livros.R
import com.example.livros.models.Livro
import com.example.livros.views.livroList
import kotlinx.coroutines.flow.*

class LivrosViewModel : ViewModel() {

    private var _mainScreenUiState: MutableStateFlow<MainScreenUiState> = MutableStateFlow(
        MainScreenUiState()
    )
    val mainScreenUiState: StateFlow<MainScreenUiState> = _mainScreenUiState.asStateFlow()


    private var _livroScreenUiState: MutableStateFlow<LivroScreenUiState> = MutableStateFlow(
        LivroScreenUiState()
    )
    val livroScreenUiState: StateFlow<LivroScreenUiState> = _livroScreenUiState.asStateFlow()

    private var _insertEditLivroScreenUiState: MutableStateFlow<InsertEditScreenUiState> =
        MutableStateFlow(
            InsertEditScreenUiState()
        )
    val insertEditScreenUiState: StateFlow<InsertEditScreenUiState> =
        _insertEditLivroScreenUiState.asStateFlow()

    var editLivro: Boolean = false
    var livroToEdit: Livro = Livro("")

    private var showFavoritesLivros = false

    private val initialLivroScreenUiState = mutableStateOf<LivroScreenUiState?>(null)

    fun onLivroTituloChange(newLivroTitulo: String) {
        _insertEditLivroScreenUiState.update { currentState ->
            currentState.copy(livroTitulo = newLivroTitulo)
        }
    }

    fun onLivroAutorChange(newLivroAutor: String) {
        _insertEditLivroScreenUiState.update { currentState ->
            currentState.copy(livroAutor = newLivroAutor)
        }
    }

    fun onDeleteLivro(livroToDelete: Livro) {
        val allLivrosTemp = _livroScreenUiState.value.allLivros.toMutableList()
        allLivrosTemp.remove(livroToDelete)
        _livroScreenUiState.value =
            _livroScreenUiState.value.copy(allLivros = allLivrosTemp.toList())
    }

    fun showFavoriteLivros() {
        val favoriteLivros = _livroScreenUiState.value.allLivros.filter { it.isFavorited }
        initialLivroScreenUiState.value = _livroScreenUiState.value
        _livroScreenUiState.value = _livroScreenUiState.value.copy(allLivros = favoriteLivros)
    }

    fun showInitialScreen() {
        initialLivroScreenUiState.value?.let { initialState ->
            _livroScreenUiState.value = initialState
        }
    }

    fun toggleShowLivros() {
        if (showFavoritesLivros) {
            showInitialScreen()
        } else {
            showFavoriteLivros()
        }
        showFavoritesLivros = !showFavoritesLivros
    }

    fun onLivroIsFavoritedChange(updatedLivro: Livro, newLivroFavorited: Boolean) {
        val allLivrosTemp = _livroScreenUiState.value.allLivros.toMutableList()
        var pos = -1
        allLivrosTemp.forEachIndexed { index, livro ->
            if (updatedLivro == livro) {
                pos = index
            }
        }
        allLivrosTemp.removeAt(pos)
        allLivrosTemp.add(pos, updatedLivro.copy(isFavorited = newLivroFavorited))
        _livroScreenUiState.update { currentState ->
            currentState.copy(allLivros = allLivrosTemp.toList())
        }
    }

    fun navigate(navController: NavController) {
        if (_mainScreenUiState.value.screenTitulo == "LISTA DE LIVROS") {
            if (editLivro) {
                _mainScreenUiState.update { currentState ->
                    currentState.copy(
                        screenTitulo = "ATUALIZAR LIVRO",
                        fabIcon = R.drawable.baseline_check_24,
                    )
                }
            } else {
                _mainScreenUiState.update { currentState ->
                    currentState.copy(
                        screenTitulo = "INSERIR LIVRO",
                        fabIcon = R.drawable.baseline_check_24,
                    )
                }
            }
            navController.navigate("insert_edit_livro")
        } else {
            _mainScreenUiState.update { currentState ->
                currentState.copy(
                    screenTitulo = "LISTA DE LIVROS",
                    fabIcon = R.drawable.baseline_add_24,
                )
            }

            if (editLivro) {
                val allLivrosTemp = _livroScreenUiState.value.allLivros.toMutableList()
                var pos = -1
                allLivrosTemp.forEachIndexed { index, livro ->
                    if (livroToEdit == livro) {
                        pos = index
                    }
                }
                allLivrosTemp.removeAt(pos)
                allLivrosTemp.add(
                    pos, livroToEdit.copy(
                        titulo = _insertEditLivroScreenUiState.value.livroTitulo,
                        autor = _insertEditLivroScreenUiState.value.livroAutor
                    )
                )
                _livroScreenUiState.update { currentState ->
                    currentState.copy(allLivros = allLivrosTemp.toList())
                }
            } else {
                _livroScreenUiState.update { currentState ->
                    currentState.copy(
                        allLivros = currentState.allLivros + Livro(
                            titulo = _insertEditLivroScreenUiState.value.livroTitulo,
                            autor = _insertEditLivroScreenUiState.value.livroAutor
                        )
                    )
                }
            }
            _insertEditLivroScreenUiState.update {
                InsertEditScreenUiState()
            }
            editLivro = false
            livroToEdit = Livro("")
            navController.navigate("livro_list") {
                popUpTo("livro_list") {
                    inclusive = true
                }
            }
        }
    }

    fun editLivro(livro: Livro, navController: NavController) {
        editLivro = true
        livroToEdit = livro
        _insertEditLivroScreenUiState.update { currentState ->
            currentState.copy(
                livroTitulo = livro.titulo,
                livroAutor = livro.autor
            )
        }
        navigate(navController)
    }
}