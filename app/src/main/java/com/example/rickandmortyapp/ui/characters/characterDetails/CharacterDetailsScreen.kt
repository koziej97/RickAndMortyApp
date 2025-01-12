package com.example.rickandmortyapp.ui.characters.characterDetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.domain.model.Character
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
                    PageLoader(modifier = Modifier.fillMaxSize())
                }
                is CharacterViewState.Error -> {
                    ErrorMessage(
                        modifier = Modifier.fillMaxSize(),
                        message = stringResource(R.string.error_getting_data),
                        onClickRetry = { onLoadCharacter(characterId) }
                    )
                }
                is CharacterViewState.Success -> {
                    CharacterDetails(viewState.character)
                }
            }
        }
    }
}

@Composable
private fun CharacterDetails(character: Character) {
    AnimatedVisibility(
        visible = true,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = 16.dp,
                        bottomEnd = 16.dp)
                    ),
                contentScale = ContentScale.FillWidth,
                model = character.imageUrl,
                contentDescription = null,
                error = BrushPainter(
                    Brush.linearGradient(
                        listOf(
                            Color.Red,
                            Color.Blue,
                        )
                    )
                ),
            )
            Text(
                text = character.name,
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CharacterDetailsScreenSuccessPreview() {
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


@Preview(showBackground = true)
@Composable
private fun CharacterDetailsScreenErrorPreview() {
    CharacterDetailsScreenContent(
        viewState = CharacterViewState.Error,
        onBackPress = { false },
        characterId = 1,
        characterName = "Rick Sanchez",
        onLoadCharacter = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun CharacterDetailsScreenLoadingPreview() {
    CharacterDetailsScreenContent(
        viewState = CharacterViewState.Loading,
        onBackPress = { false },
        characterId = 1,
        characterName = "Rick Sanchez",
        onLoadCharacter = {}
    )
}
