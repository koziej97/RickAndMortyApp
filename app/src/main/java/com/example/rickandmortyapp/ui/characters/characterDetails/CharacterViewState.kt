package com.example.rickandmortyapp.ui.characters.characterDetails

import com.example.rickandmortyapp.domain.model.Character

sealed class CharacterViewState {
    data object Loading : CharacterViewState()
    data object Error : CharacterViewState()
    data class Success(val character: Character) : CharacterViewState()
}