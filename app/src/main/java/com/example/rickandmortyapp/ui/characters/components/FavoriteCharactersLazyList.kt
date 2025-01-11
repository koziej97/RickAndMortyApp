package com.example.rickandmortyapp.ui.characters.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.domain.model.Character
import com.example.rickandmortyapp.ui.characters.allCharacters.viewStates.FavoriteCharactersViewState
import com.example.rickandmortyapp.ui.characters.utils.createListOfCharactersForPreview

@Composable
fun FavoriteCharactersLazyList(
    favoritesUiState: FavoriteCharactersViewState,
    toggleFavoriteButton: (Character) -> Unit,
    onItemClick: (Int, String) -> Unit
) {
    when (favoritesUiState) {
        is FavoriteCharactersViewState.Loading -> {
            PageLoader(modifier = Modifier.fillMaxSize())
        }
        is FavoriteCharactersViewState.Empty -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.no_favorites_characters_message),
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }
        }
        is FavoriteCharactersViewState.Success -> {
            val characters = favoritesUiState.characters
            LazyColumn {
                items(items = characters ?: emptyList(), key = { it.id }) { item ->
                    CharacterItem(
                        character = item,
                        onFavoriteClick = { toggleFavoriteButton(item) },
                        onItemClick = onItemClick
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoriteCharactersLazyListPreview() {
    FavoriteCharactersLazyList(
        favoritesUiState = FavoriteCharactersViewState.Success(
            createListOfCharactersForPreview()
        ),
        toggleFavoriteButton = {},
        onItemClick = { _, _ -> }
    )
}
