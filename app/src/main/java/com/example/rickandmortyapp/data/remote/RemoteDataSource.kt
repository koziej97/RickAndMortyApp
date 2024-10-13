package com.example.rickandmortyapp.data.remote

import com.example.rickandmortyapp.data.remote.model.CharactersResponse

interface RemoteDataSource {
    suspend fun fetchAllCharacters(page: Int):  Result<CharactersResponse>
}