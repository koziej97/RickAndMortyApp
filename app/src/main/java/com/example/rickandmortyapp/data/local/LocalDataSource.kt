package com.example.rickandmortyapp.data.local

import com.example.rickandmortyapp.data.local.database.model.CharacterEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getFavorites(): Flow<List<CharacterEntity>>
    suspend fun addToFavorites(characterEntity: CharacterEntity)
    suspend fun removeFromFavorites(characterEntity: CharacterEntity)
}