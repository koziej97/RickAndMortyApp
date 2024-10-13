package com.example.rickandmortyapp.ui.characters

import com.example.rickandmortyapp.data.remote.model.CharacterDTO

sealed class AllCharactersUiState {
    data object Loading : AllCharactersUiState()
    data class Error(val message: String) : AllCharactersUiState()
    data class Success(val characters: List<CharacterDTO>?) : AllCharactersUiState()
}