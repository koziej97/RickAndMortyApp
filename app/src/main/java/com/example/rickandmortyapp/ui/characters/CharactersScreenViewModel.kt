package com.example.rickandmortyapp.ui.characters

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.rickandmortyapp.domain.model.Character
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.rickandmortyapp.domain.repository.CharactersRepository
import com.example.rickandmortyapp.domain.usecase.UpdateCharactersFavoriteStatus
import com.example.rickandmortyapp.ui.characters.uiStates.FavoriteCharactersUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersScreenViewModel @Inject constructor(
    private val charactersRepository: CharactersRepository,
    private val updateCharactersFavoriteStatus: UpdateCharactersFavoriteStatus
): ViewModel() {

    private val _allCharactersFlow = MutableStateFlow<PagingData<Character>>(PagingData.empty())
    val allCharactersFlow: StateFlow<PagingData<Character>> = _allCharactersFlow

    private val _favoritesUiState = mutableStateOf<FavoriteCharactersUiState>(FavoriteCharactersUiState.Loading)
    val favoritesUiState: State<FavoriteCharactersUiState> = _favoritesUiState

    var isShowingFavorites by mutableStateOf(false)
        private set

    fun fetchData() {
        fetchFavorites()
        fetchAllCharacters()
    }

    fun showAllCharacters() {
        isShowingFavorites = false
    }

    fun showFavoritesCharacters() {
        isShowingFavorites = true
    }

    fun toggleFavorite(character: Character) {
        viewModelScope.launch {
            val updatedCharacter: Character?
            if (character.isFavorite) {
                updatedCharacter = character.copy(isFavorite = false)
                charactersRepository.removeFromFavorites(updatedCharacter)
            } else {
                updatedCharacter = character.copy(isFavorite = true)
                charactersRepository.addToFavorites(updatedCharacter)
            }
            _allCharactersFlow.value = _allCharactersFlow.value.updateCharacter(updatedCharacter)
        }
    }

    private val favoritesFlow = charactersRepository.getFavorites()

    private fun fetchFavorites() {
        viewModelScope.launch {
            _favoritesUiState.value = FavoriteCharactersUiState.Loading
            favoritesFlow.collect {
                if (it.isEmpty()) {
                    _favoritesUiState.value = FavoriteCharactersUiState.Empty
                } else {
                    _favoritesUiState.value = FavoriteCharactersUiState.Success(it)
                }
            }
        }
    }

    private fun fetchAllCharacters() {
        viewModelScope.launch {
            charactersRepository.getCharactersFlow()
                .cachedIn(viewModelScope)
                .combine(favoritesFlow) { pagingData, favoriteList ->
                    updateCharactersFavoriteStatus.execute(pagingData, favoriteList)
                }
                .collectLatest { updatedPagingData ->
                    _allCharactersFlow.value = updatedPagingData
                }
        }
    }

    private fun PagingData<Character>.updateCharacter(updatedCharacter: Character): PagingData<Character> {
        return this.map { character ->
            if (character.id == updatedCharacter.id) {
                updatedCharacter
            } else {
                character
            }
        }
    }
}