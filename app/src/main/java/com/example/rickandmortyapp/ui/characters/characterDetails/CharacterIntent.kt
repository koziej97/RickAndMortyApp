package com.example.rickandmortyapp.ui.characters.characterDetails

sealed class CharacterIntent {
    data class LoadCharacter(val id: Int) : CharacterIntent()
}