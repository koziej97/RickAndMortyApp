package com.example.rickandmortyapp.data.remote

import com.example.rickandmortyapp.data.remote.model.CharacterDTO
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor (private val apiService: ApiService): RemoteDataSource {

    override suspend fun fetchAllCharacters(): Result<List<CharacterDTO>> {
        return try {
            val response = apiService.fetchAllCharacters(0)

            if (response.isSuccessful) {
                response.body()?.results?.let { Result.success(it) }
                    ?: Result.failure(Exception("No characters found"))
            } else {
                Result.failure(Exception("Error ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}