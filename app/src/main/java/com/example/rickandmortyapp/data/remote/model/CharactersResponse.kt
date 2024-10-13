package com.example.rickandmortyapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class CharactersResponse(
    @SerializedName("info")
    val info: InfoDTO,
    @SerializedName("results")
    val results: List<CharacterDTO>
)