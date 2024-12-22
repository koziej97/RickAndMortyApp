package com.example.rickandmortyapp.ui.characters.allCharacters

sealed class AllCharactersIntent {
    data object LoadCharacters : AllCharactersIntent()
    data object ShowAllCharacters : AllCharactersIntent()
    data object ShowFavoritesCharacters : AllCharactersIntent()
}