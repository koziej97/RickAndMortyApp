package com.example.rickandmortyapp.ui.characters.allCharacters.uiStates

import com.example.rickandmortyapp.domain.model.Character

sealed class FavoriteCharactersUiState {
    data object Loading : FavoriteCharactersUiState()
    data object Empty : FavoriteCharactersUiState()
    data class Success(val characters: List<Character>?) : FavoriteCharactersUiState()
}