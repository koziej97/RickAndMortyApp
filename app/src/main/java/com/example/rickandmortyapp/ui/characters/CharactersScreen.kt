package com.example.rickandmortyapp.ui.characters

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.ui.characters.components.AllCharactersLazyList
import com.example.rickandmortyapp.ui.characters.components.FavoriteCharactersLazyList
import com.example.rickandmortyapp.ui.characters.components.NavigationBarCustom

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersScreen() {
    val viewModel: CharactersScreenViewModel = hiltViewModel()
    val favoritesUiState by viewModel.favoritesUiState
    val allCharactersPagingItems = viewModel.allCharactersFlow.collectAsLazyPagingItems()

    LaunchedEffect(true) {
        viewModel.fetchData()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(stringResource(R.string.rick_and_morty_characters))
                }
            )
        },
        bottomBar = {
            NavigationBarCustom(
                isShowingFavorites = viewModel.isShowingFavorites,
                showAllCharacters = { viewModel.showAllCharacters() },
                showFavoritesCharacters = { viewModel.showFavoritesCharacters() }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
        ) {
            if (viewModel.isShowingFavorites) {
                FavoriteCharactersLazyList(
                    favoritesUiState = favoritesUiState,
                    toggleFavoriteButton = viewModel::toggleFavorite)
            } else {
                AllCharactersLazyList(
                    charactersPagingItems = allCharactersPagingItems,
                    toggleFavoriteButton = viewModel::toggleFavorite
                )
            }
        }
    }
}
