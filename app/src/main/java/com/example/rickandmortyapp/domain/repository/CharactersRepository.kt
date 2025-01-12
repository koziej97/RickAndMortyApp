package com.example.rickandmortyapp.domain.repository

import androidx.paging.PagingData
import com.example.rickandmortyapp.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    fun getCharactersFlow(): Flow<PagingData<Character>>
    fun getFavorites(): Flow<List<Character>>
    suspend fun addToFavorites(character: Character): Result<Unit>
    suspend fun removeFromFavorites(character: Character): Result<Unit>
    fun getCharacterDataFlow(id: Int): Flow<Result<Character>>
}