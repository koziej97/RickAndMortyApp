package com.example.rickandmortyapp.ui.characters

import com.example.rickandmortyapp.domain.model.Character
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.rickandmortyapp.domain.repository.CharactersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersScreenViewModel @Inject constructor(
    private val charactersRepository: CharactersRepository
): ViewModel() {

    private val _allCharactersFlow = MutableStateFlow<PagingData<Character>>(PagingData.empty())
    val allCharactersFlow: StateFlow<PagingData<Character>> = _allCharactersFlow

    fun fetchData() {
        fetchAllCharacters()
    }

    fun toggleFavorite(character: Character) {
        viewModelScope.launch {
            val updatedCharacter: Character?
            if (character.isFavorite) {
                updatedCharacter = character.copy(isFavorite = false)
            } else {
                updatedCharacter = character.copy(isFavorite = true)
            }
            _allCharactersFlow.value = _allCharactersFlow.value.updateCharacter(updatedCharacter)
        }
    }

    private fun fetchAllCharacters() {
        viewModelScope.launch {
            charactersRepository.getCharactersPagingSource()
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _allCharactersFlow.value = pagingData
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