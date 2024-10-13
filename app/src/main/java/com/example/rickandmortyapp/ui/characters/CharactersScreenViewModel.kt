package com.example.rickandmortyapp.ui.characters

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.data.remote.ApiService
import com.example.rickandmortyapp.data.remote.model.CharacterDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersScreenViewModel @Inject constructor(
    private val apiService: ApiService
): ViewModel() {

    private val _allItemsUiState = mutableStateOf<AllCharactersUiState>(AllCharactersUiState.Loading)
    val allItemsUiState: State<AllCharactersUiState> = _allItemsUiState

    fun fetchData() {
        viewModelScope.launch {
            _allItemsUiState.value = AllCharactersUiState.Loading
            val charactersResult = fetchAllCharacters(0)
            when {
                charactersResult.isSuccess -> {
                    _allItemsUiState.value = AllCharactersUiState.Success(charactersResult.getOrNull())
                }
                charactersResult.isFailure -> {
                    _allItemsUiState.value = AllCharactersUiState.Error("Error getting data")
                }
            }
        }
    }


    private suspend fun fetchAllCharacters(page: Int): Result<List<CharacterDTO>> {
        return try {
            val response = apiService.fetchAllCharacters(page)

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