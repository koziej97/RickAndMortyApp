package com.example.rickandmortyapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class InfoDTO(
    @SerializedName("count")
    val count: Int,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("next")
    val next: String,
    @SerializedName("prev")
    val prev: String?
)