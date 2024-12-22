package com.example.rickandmortyapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.rickandmortyapp.ui.characters.allCharacters.AllCharactersScreen
import com.example.rickandmortyapp.ui.characters.characterDetails.CharacterDetailsScreen

@Composable
fun Navigation(
    navController: NavHostController,
    darkTheme: Boolean,
    onThemeUpdated: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.AllCharacters
    ) {
        composable<Destinations.AllCharacters> {
            AllCharactersScreen(
                darkTheme = darkTheme,
                onThemeUpdated = onThemeUpdated,
                onCharacterClick = { id ->
                    navController.navigate(
                        Destinations.CharacterDetails(id)
                    )
                }
            )
        }

        composable<Destinations.CharacterDetails> {
            val args = it.toRoute<Destinations.CharacterDetails>()
            CharacterDetailsScreen(
                id = args.id,
            )
        }
    }
}