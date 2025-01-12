package com.example.rickandmortyapp.ui.characters.allCharacters

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.domain.model.Character
import com.example.rickandmortyapp.ui.characters.allCharacters.viewStates.AllCharactersViewState
import com.example.rickandmortyapp.ui.characters.allCharacters.viewStates.FavoriteCharactersViewState
import com.example.rickandmortyapp.ui.characters.components.AllCharactersLazyList
import com.example.rickandmortyapp.ui.characters.components.FavoriteCharactersLazyList
import com.example.rickandmortyapp.ui.characters.components.NavigationBarCustom
import com.example.rickandmortyapp.ui.characters.utils.createListOfCharactersForPreview
import kotlinx.coroutines.flow.flowOf

@Composable
fun AllCharactersScreen(
    darkTheme: Boolean,
    onThemeUpdated: () -> Unit,
    onCharacterClick: (Int, String) -> Unit
) {
    val viewModel: AllCharactersScreenViewModel = hiltViewModel()
    val viewState by viewModel.viewState.collectAsState()

    LaunchedEffect(true) {
        viewModel.handleIntent(AllCharactersIntent.LoadCharacters)
    }

    AllCharactersScreenContent(
        darkTheme = darkTheme,
        onThemeUpdated = onThemeUpdated,
        onCharacterClick = onCharacterClick,
        viewState = viewState,
        onShowAllCharacters = {
            viewModel.handleIntent(AllCharactersIntent.ShowAllCharacters)
        },
        onShowFavoriteCharacters = {
            viewModel.handleIntent(AllCharactersIntent.ShowFavoritesCharacters)
        },
        onToggleFavorite = {
            viewModel.handleIntent(AllCharactersIntent.ToggleFavorite(it))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AllCharactersScreenContent(
    darkTheme: Boolean,
    onThemeUpdated: () -> Unit,
    onCharacterClick: (Int, String) -> Unit,
    viewState: AllCharactersViewState,
    onShowAllCharacters: () -> Unit,
    onShowFavoriteCharacters: () -> Unit,
    onToggleFavorite: (Character) -> Unit,
) {
    val allCharactersPagingItems = viewState.allCharacters.collectAsLazyPagingItems()

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
                isShowingFavorites = viewState.isShowingFavorites,
                showAllCharacters = onShowAllCharacters,
                showFavoritesCharacters = onShowFavoriteCharacters
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
        ) {
            Crossfade(
                targetState = viewState.isShowingFavorites,
                animationSpec = tween(durationMillis = 750),
                label = ""
            ) { isShowingFavorites ->
                if (isShowingFavorites) {
                    FavoriteCharactersLazyList(
                        favoritesUiState = viewState.favoritesState,
                        toggleFavoriteButton = onToggleFavorite,
                        onItemClick = onCharacterClick
                    )
                } else {
                    AllCharactersLazyList(
                        charactersPagingItems = allCharactersPagingItems,
                        toggleFavoriteButton = onToggleFavorite,
                        onItemClick = onCharacterClick
                    )
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AllCharactersScreenPreview() {
    AllCharactersScreenContent(
        darkTheme = false,
        onThemeUpdated = {},
        onCharacterClick = { _, _ -> },
        viewState = createViewStateForPreview(),
        onShowAllCharacters = {},
        onShowFavoriteCharacters = {},
        onToggleFavorite = {}
    )
}

private fun createViewStateForPreview() = AllCharactersViewState(
    allCharacters = flowOf(PagingData.empty()),
    favoritesState = FavoriteCharactersViewState.Success(
        createListOfCharactersForPreview()
    ),
    isShowingFavorites = true
)
