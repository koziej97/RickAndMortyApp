package com.example.rickandmortyapp.ui.characters.allCharacters.viewStates

import com.example.rickandmortyapp.domain.model.Character

sealed class FavoriteCharactersViewState {
    data object Loading : FavoriteCharactersViewState()
    data object Empty : FavoriteCharactersViewState()
    data class Success(val characters: List<Character>?) : FavoriteCharactersViewState()
}