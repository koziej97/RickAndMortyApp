package com.example.rickandmortyapp.ui.characters

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharactersScreenViewModel @Inject constructor(): ViewModel() {

    val screenName = "Characters Screen"

}