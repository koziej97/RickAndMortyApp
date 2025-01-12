package com.example.rickandmortyapp.ui.characters.utils

import com.example.rickandmortyapp.domain.model.Character
import com.example.rickandmortyapp.domain.model.Status

fun createListOfCharactersForPreview(): List<Character> {
    return (1..4).map { createCharacterForPreview(it) }
}

fun createCharacterForPreview(id: Int = 1) = Character(
    id = id,
    name = "Rick Sanchez",
    imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
    isFavorite = id % 2 == 0,
    status = Status.ALIVE,
    species = "Human",
    gender = "Male",
    origin = "Earth (C-137)",
    lastLocation = "Citadel of Ricks"
)