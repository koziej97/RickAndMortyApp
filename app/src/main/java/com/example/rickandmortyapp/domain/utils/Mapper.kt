package com.example.rickandmortyapp.domain.utils

import com.example.rickandmortyapp.data.remote.model.CharacterDTO
import com.example.rickandmortyapp.domain.model.Character

fun CharacterDTO.toCharacter(): Character {
    return Character(
        id = this.id,
        name = this.name,
        imageUrl = this.image
    )
}