package com.example.rickandmortyapp.ui.characters.allCharacters.viewStates

import androidx.paging.PagingData
import com.example.rickandmortyapp.domain.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class AllCharactersViewState (
    val allCharacters: Flow<PagingData<Character>> = flowOf(PagingData.empty()),
    val favoritesState: FavoriteCharactersViewState = FavoriteCharactersViewState.Loading,
    val isShowingFavorites: Boolean = false
)