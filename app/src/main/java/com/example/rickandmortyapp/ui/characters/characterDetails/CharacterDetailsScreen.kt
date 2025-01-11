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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.ui.characters.components.ErrorMessage
import com.example.rickandmortyapp.ui.characters.components.PageLoader
import com.example.rickandmortyapp.ui.characters.utils.createCharacterForPreview

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

    CharacterDetailsScreenContent(
        viewState = viewState,
        onBackPress = onBackPress,
        characterId = characterId,
        characterName = characterName,
        onLoadCharacter = { viewModel.handleIntent(CharacterIntent.LoadCharacter(it)) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharacterDetailsScreenContent(
    viewState: CharacterViewState,
    onBackPress: () -> Boolean,
    characterId: Int,
    characterName: String,
    onLoadCharacter: (Int) -> Unit,
) {
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
                        onClickRetry = { onLoadCharacter(characterId) }
                    )
                }
                is CharacterViewState.Success -> {
                    val character = viewState.character
                    Text(character.name)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CharacterDetailsScreenPreview() {
    CharacterDetailsScreenContent(
        viewState = CharacterViewState.Success(
            createCharacterForPreview()
        ),
        onBackPress = { false },
        characterId = 1,
        characterName = "Rick Sanchez",
        onLoadCharacter = {}
    )
}
