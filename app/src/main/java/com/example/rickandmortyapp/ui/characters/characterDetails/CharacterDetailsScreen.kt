package com.example.rickandmortyapp.ui.characters.characterDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.ui.characters.components.ErrorMessage
import com.example.rickandmortyapp.ui.characters.components.PageLoader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailsScreen(
    characterId: Int,
    characterName: String,
    onBackPress: () -> Boolean
) {

    val viewModel: CharacterDetailsViewModel = hiltViewModel()
    val viewState by viewModel.viewState.collectAsState()

    LaunchedEffect(true) {
        viewModel.handleIntent(CharacterIntent.LoadCharacter(characterId))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                navigationIcon = {
                    IconButton(onClick = { onBackPress() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "BackButton",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    Text(characterName)
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
        ) {
            when (viewState) {
                is CharacterViewState.Loading -> {
                    PageLoader()
                }
                is CharacterViewState.Error -> {
                    ErrorMessage(
                        modifier = Modifier.fillMaxSize(),
                        message = stringResource(R.string.error_getting_data),
                        onClickRetry = {
                            viewModel.handleIntent(CharacterIntent.LoadCharacter(characterId))
                        }
                    )
                }
                is CharacterViewState.Success -> {
                    val character = (viewState as CharacterViewState.Success).character
                    Text(character.name)
                }
            }
        }
    }
}