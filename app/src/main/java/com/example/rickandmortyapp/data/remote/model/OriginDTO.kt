package com.example.rickandmortyapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class OriginDTO(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String,
)