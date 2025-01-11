package com.example.rickandmortyapp.ui.characters.allCharacters

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.ui.characters.components.AllCharactersLazyList
import com.example.rickandmortyapp.ui.characters.components.FavoriteCharactersLazyList
import com.example.rickandmortyapp.ui.characters.components.NavigationBarCustom

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllCharactersScreen(
    darkTheme: Boolean,
    onThemeUpdated: () -> Unit,
    onCharacterClick: (Int, String) -> Unit
) {
    val viewModel: AllCharactersScreenViewModel = hiltViewModel()
    val favoritesUiState by viewModel.favoritesUiState
    val allCharactersPagingItems = viewModel.allCharactersFlow.collectAsLazyPagingItems()

    LaunchedEffect(true) {
        viewModel.handleIntent(AllCharactersIntent.LoadCharacters)
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
                },
                actions = {
                    IconButton(onClick = {
                        onThemeUpdated()
                    }) {
                        if (darkTheme) {
                            Icon(
                                painter = painterResource(id = R.drawable.sun),
                                contentDescription = "Sun Icon",
                                modifier = Modifier.size(24.dp)
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.moon),
                                contentDescription = "Moon Icon",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            NavigationBarCustom(
                isShowingFavorites = viewModel.isShowingFavorites,
                showAllCharacters = {
                    viewModel.handleIntent(AllCharactersIntent.ShowAllCharacters) },
                showFavoritesCharacters = {
                    viewModel.handleIntent(AllCharactersIntent.ShowFavoritesCharacters) }
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
                    toggleFavoriteButton = {
                        viewModel.handleIntent(AllCharactersIntent.ToggleFavorite(it)) },
                    onItemClick = onCharacterClick
                )
            } else {
                AllCharactersLazyList(
                    charactersPagingItems = allCharactersPagingItems,
                    toggleFavoriteButton = {
                        viewModel.handleIntent(AllCharactersIntent.ToggleFavorite(it)) },
                    onItemClick = onCharacterClick
                )
            }
        }
    }
}