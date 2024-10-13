package com.example.rickandmortyapp.data.local

import com.example.rickandmortyapp.data.local.database.CharactersDao
import com.example.rickandmortyapp.data.local.database.model.CharacterEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor (private val charactersDao: CharactersDao): LocalDataSource {

    override fun getFavorites(): Flow<List<CharacterEntity>> {
        return charactersDao.getAllCharacters()
    }

    override suspend fun addToFavorites(characterEntity: CharacterEntity) {
        charactersDao.insert(characterEntity)
    }

    override suspend fun removeFromFavorites(characterEntity: CharacterEntity) {
        charactersDao.delete(characterEntity)
    }
}