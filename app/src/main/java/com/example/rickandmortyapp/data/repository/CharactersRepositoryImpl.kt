package com.example.rickandmortyapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rickandmortyapp.data.local.LocalDataSource
import com.example.rickandmortyapp.data.remote.CharacterPagingSource
import com.example.rickandmortyapp.data.remote.RemoteDataSource
import com.example.rickandmortyapp.domain.model.Character
import com.example.rickandmortyapp.domain.repository.CharactersRepository
import com.example.rickandmortyapp.domain.utils.toCharacter
import com.example.rickandmortyapp.domain.utils.toCharacterEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): CharactersRepository {

    override fun getCharactersPagingSource(): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { CharacterPagingSource(remoteDataSource) }
        ).flow
    }

    override fun getFavorites(): Flow<List<Character>> {
        return localDataSource.getFavorites().map { entityList ->
            entityList.map { characterEntity ->
                characterEntity.toCharacter()
            }
        }
    }

    override suspend fun addToFavorites(character: Character): Result<Unit> {
        return try {
            localDataSource.addToFavorites(character.toCharacterEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun removeFromFavorites(character: Character): Result<Unit> {
        return try {
            localDataSource.removeFromFavorites(character.toCharacterEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}