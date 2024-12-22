package com.example.rickandmortyapp.ui.characters.characterDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.domain.repository.CharactersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val charactersRepository: CharactersRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow<CharacterViewState>(CharacterViewState.Loading)
    val viewState: StateFlow<CharacterViewState> = _viewState

    fun handleIntent(intent: CharacterIntent) {
        viewModelScope.launch {
            when(intent) {
                is CharacterIntent.LoadCharacter -> fetchCharacter(intent.id)
            }
        }
    }

    private fun fetchCharacter(id: Int) {
        viewModelScope.launch {
            charactersRepository.getCharacterData(id)
                .onSuccess { character ->
                    _viewState.value = CharacterViewState.Success(character)
                }
                .onFailure {
                    _viewState.value = CharacterViewState.Error
                }
        }
    }

}