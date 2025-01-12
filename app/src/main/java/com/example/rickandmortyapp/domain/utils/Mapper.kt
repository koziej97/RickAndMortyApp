package com.example.rickandmortyapp.domain.utils

import com.example.rickandmortyapp.data.local.database.model.CharacterEntity
import com.example.rickandmortyapp.data.remote.model.CharacterDTO
import com.example.rickandmortyapp.domain.model.Character
import com.example.rickandmortyapp.domain.model.Status

fun CharacterDTO.toCharacter(): Character {
    return Character(
        id = this.id,
        name = this.name,
        imageUrl = this.image,
        status = Status.valueOf(this.status.uppercase()),
        species = this.species,
        gender = this.gender,
        origin = this.origin.name,
        lastLocation = this.location.name
    )
}

fun CharacterEntity.toCharacter(): Character {
    return Character(
        id = this.id.toInt(),
        name = this.name,
        imageUrl = this.imageUrl,
        status = Status.valueOf(this.status.uppercase()),
        species = this.species,
        gender = this.gender,
        origin = this.origin,
        lastLocation = this.lastLocation,
        isFavorite = this.isFavorite
    )
}

fun Character.toCharacterEntity(): CharacterEntity {
    return CharacterEntity(
        id = this.id.toLong(),
        name = this.name,
        isFavorite = this.isFavorite,
        imageUrl = this.imageUrl,
        status = this.status.toString(),
        species = this.species,
        gender = this.gender,
        origin = this.origin,
        lastLocation = this.lastLocation
    )
}
