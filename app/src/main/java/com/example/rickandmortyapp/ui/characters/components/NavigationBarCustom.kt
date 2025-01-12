package com.example.rickandmortyapp.ui.characters.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NavigationBarCustom(
    isShowingFavorites: Boolean,
    showAllCharacters: () -> Unit,
    showFavoritesCharacters: () -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = null) },
            label = { Text("All Characters") },
            selected = !isShowingFavorites,
            onClick = {
                showAllCharacters()
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Favorite, contentDescription = null) },
            label = { Text("Favorite Characters") },
            selected = isShowingFavorites,
            onClick = {
                showFavoritesCharacters()
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NavigationBarCustomPreview() {
    NavigationBarCustom(
        isShowingFavorites = false,
        showAllCharacters = {},
        showFavoritesCharacters = {}
    )
}
