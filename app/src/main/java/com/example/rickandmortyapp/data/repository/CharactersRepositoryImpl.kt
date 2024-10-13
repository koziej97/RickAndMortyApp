package com.example.rickandmortyapp.data.repository

import com.example.rickandmortyapp.data.remote.RemoteDataSource
import com.example.rickandmortyapp.data.remote.model.CharacterDTO
import com.example.rickandmortyapp.domain.repository.CharactersRepository
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): CharactersRepository {

    override suspend fun getAllCharacters(): Result<List<CharacterDTO>> {
        return remoteDataSource.fetchAllCharacters()
    }

}