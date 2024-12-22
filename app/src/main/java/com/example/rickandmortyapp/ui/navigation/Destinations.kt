package com.example.rickandmortyapp.ui.navigation

import kotlinx.serialization.Serializable

object Destinations {

    @Serializable
    object AllCharacters

    @Serializable
    data class CharacterDetails(
        val id: Int,
        val name: String
    )
}