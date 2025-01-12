package com.example.rickandmortyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.example.rickandmortyapp.ui.navigation.Navigation
import com.example.rickandmortyapp.ui.theme.RickAndMortyAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isSystemInDarkTheme = isSystemInDarkTheme()
            var darkTheme by remember { mutableStateOf(isSystemInDarkTheme) }
            val onThemeUpdated = { darkTheme = !darkTheme }

            RickAndMortyAppTheme(darkTheme = darkTheme) {
                val navController = rememberNavController()
                Navigation(
                    navController = navController,
                    darkTheme = darkTheme,
                    onThemeUpdated = onThemeUpdated
                )
            }
        }
    }
}