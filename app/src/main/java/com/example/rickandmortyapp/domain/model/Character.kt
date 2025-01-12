package com.example.rickandmortyapp.domain.model

data class Character(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val status: Status,
    val species: String,
    val gender: String,
    val origin: String,
    val lastLocation: String,
    val isFavorite: Boolean = false
)

enum class Status {
    ALIVE,
    DEAD,
    UNKNOWN
}
