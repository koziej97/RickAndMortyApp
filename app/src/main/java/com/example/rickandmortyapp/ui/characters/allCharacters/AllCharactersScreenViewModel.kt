package com.example.rickandmortyapp.ui.characters.allCharacters

import com.example.rickandmortyapp.domain.model.Character
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.rickandmortyapp.domain.repository.CharactersRepository
import com.example.rickandmortyapp.domain.usecase.UpdateCharactersFavoriteStatus
import com.example.rickandmortyapp.ui.characters.allCharacters.viewStates.AllCharactersViewState
import com.example.rickandmortyapp.ui.characters.allCharacters.viewStates.FavoriteCharactersViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllCharactersScreenViewModel @Inject constructor(
    private val charactersRepository: CharactersRepository,
    private val updateCharactersFavoriteStatus: UpdateCharactersFavoriteStatus
) : ViewModel() {

    private val _viewState = MutableStateFlow(AllCharactersViewState())
    val viewState: StateFlow<AllCharactersViewState> = _viewState

    private val allCharactersFlow: MutableStateFlow<PagingData<Character>> =
        MutableStateFlow(PagingData.empty())

    private val favoritesUiState: MutableStateFlow<FavoriteCharactersViewState> =
        MutableStateFlow(FavoriteCharactersViewState.Loading)

    private val isShowingFavorites: MutableStateFlow<Boolean> = MutableStateFlow(false)

    init {
        combineStateFlows()
    }

    private fun combineStateFlows() {
        viewModelScope.launch {
            combine(
                allCharactersFlow,
                favoritesUiState,
                isShowingFavorites
            ) { _, favoritesState, isShowingFavorites ->
                AllCharactersViewState(
                    allCharacters = allCharactersFlow,
                    favoritesState = favoritesState,
                    isShowingFavorites = isShowingFavorites
                )
            }.collect { newState ->
                _viewState.value = newState
            }
        }
    }

    fun handleIntent(intent: AllCharactersIntent) {
        viewModelScope.launch {
            when(intent) {
                is AllCharactersIntent.LoadCharacters -> fetchData()
                is AllCharactersIntent.ShowAllCharacters -> showAllCharacters()
                is AllCharactersIntent.ShowFavoritesCharacters -> showFavoritesCharacters()
                is AllCharactersIntent.ToggleFavorite -> toggleFavorite(intent.character)
            }
        }
    }

    private fun showAllCharacters() {
        isShowingFavorites.value = false
    }

    private fun showFavoritesCharacters() {
        isShowingFavorites.value = true
    }

    private fun toggleFavorite(character: Character) {
        viewModelScope.launch {
            val updatedCharacter: Character?
            if (character.isFavorite) {
                updatedCharacter = character.copy(isFavorite = false)
                charactersRepository.removeFromFavorites(updatedCharacter)
            } else {
                updatedCharacter = character.copy(isFavorite = true)
                charactersRepository.addToFavorites(updatedCharacter)
            }
            allCharactersFlow.value = allCharactersFlow.value.updateCharacter(updatedCharacter)
        }
    }

    private fun fetchData() {
        fetchFavorites()
        fetchAllCharacters()
    }

    private val favoritesFlow = charactersRepository.getFavorites()

    private fun fetchFavorites() {
        viewModelScope.launch {
            favoritesUiState.value = FavoriteCharactersViewState.Loading
            favoritesFlow.collect {
                if (it.isEmpty()) {
                    favoritesUiState.value = FavoriteCharactersViewState.Empty
                } else {
                    favoritesUiState.value = FavoriteCharactersViewState.Success(it)
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
                    allCharactersFlow.value = updatedPagingData
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