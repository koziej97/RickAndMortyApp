package com.example.rickandmortyapp.ui.characters.allCharacters

import com.example.rickandmortyapp.domain.model.Character

sealed class AllCharactersIntent {
    data object LoadCharacters : AllCharactersIntent()
    data object ShowAllCharacters : AllCharactersIntent()
    data object ShowFavoritesCharacters : AllCharactersIntent()
    data class ToggleFavorite(val character: Character) : AllCharactersIntent()
}