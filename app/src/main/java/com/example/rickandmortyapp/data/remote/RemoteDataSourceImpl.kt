package com.example.rickandmortyapp.data.remote

import com.example.rickandmortyapp.data.remote.model.CharactersResponse
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor (private val apiService: ApiService): RemoteDataSource {

    override suspend fun fetchAllCharacters(page: Int): Result<CharactersResponse> {
        return try {
            val response = apiService.fetchAllCharacters(page)

            if (response.isSuccessful) {
                response.body()?.let { Result.success(it) }
                    ?: Result.failure(Exception("No characters found"))
            } else {
                Result.failure(Exception("Error ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}