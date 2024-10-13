package com.example.rickandmortyapp.data.remote

import com.example.rickandmortyapp.data.remote.model.CharactersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val BASE_URL = "https://rickandmortyapi.com/api/"
    }

    @GET("character")
    suspend fun fetchAllCharacters(
        @Query("page")
        page: Int
    ): Response<CharactersResponse>

}