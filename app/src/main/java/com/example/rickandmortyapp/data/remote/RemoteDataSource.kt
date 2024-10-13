package com.example.rickandmortyapp.data.remote

import com.example.rickandmortyapp.data.remote.model.CharacterDTO

interface RemoteDataSource {
    suspend fun fetchAllCharacters():  Result<List<CharacterDTO>>
}