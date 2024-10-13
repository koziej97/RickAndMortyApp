package com.example.rickandmortyapp.ui.characters

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.domain.repository.CharactersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersScreenViewModel @Inject constructor(
    private val charactersRepository: CharactersRepository
): ViewModel() {

    private val _allItemsUiState = mutableStateOf<AllCharactersUiState>(AllCharactersUiState.Loading)
    val allItemsUiState: State<AllCharactersUiState> = _allItemsUiState

    fun fetchData() {
        fetchAllCharacters()
    }

    private fun fetchAllCharacters() {
        viewModelScope.launch {
            _allItemsUiState.value = AllCharactersUiState.Loading
            val charactersResult = charactersRepository.getAllCharacters()
            when {
                charactersResult.isSuccess -> {
                    _allItemsUiState.value = AllCharactersUiState.Success(charactersResult.getOrNull())
                }
                charactersResult.isFailure -> {
                    _allItemsUiState.value = AllCharactersUiState.Error("Error getting data")
                }
            }
        }
    }

}