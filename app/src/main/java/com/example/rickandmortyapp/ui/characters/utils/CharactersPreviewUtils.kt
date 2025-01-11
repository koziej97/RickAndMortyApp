package com.example.rickandmortyapp.ui.characters.utils

import com.example.rickandmortyapp.domain.model.Character

fun createListOfCharactersForPreview(): List<Character> {
    return (1..4).map { createCharacterForPreview(it) }
}

fun createCharacterForPreview(id: Int = 1) = Character(
    id = id,
    name = "Rick Sanchez",
    imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
    isFavorite = id % 2 == 0
)