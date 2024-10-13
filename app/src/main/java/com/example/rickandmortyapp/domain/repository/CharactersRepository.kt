package com.example.rickandmortyapp.domain.repository

import com.example.rickandmortyapp.data.remote.model.CharacterDTO

interface CharactersRepository {
    suspend fun getAllCharacters(): Result<List<CharacterDTO>>
}