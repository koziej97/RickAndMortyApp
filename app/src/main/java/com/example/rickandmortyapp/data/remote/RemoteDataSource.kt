package com.example.rickandmortyapp.data.remote

import com.example.rickandmortyapp.data.remote.model.CharacterDTO
import com.example.rickandmortyapp.data.remote.model.CharactersResponse

interface RemoteDataSource {
    suspend fun fetchAllCharacters(page: Int):  Result<CharactersResponse>
    suspend fun fetchCharacter(id: Int): Result<CharacterDTO>
}