package com.example.rickandmortyapp.ui.characters

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.ui.characters.components.CharacterItem
import com.example.rickandmortyapp.ui.characters.components.ErrorMessage
import com.example.rickandmortyapp.ui.characters.components.PageLoader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersScreen() {
    val viewModel: CharactersScreenViewModel = hiltViewModel()

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
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
        ) {
            LazyColumn {
                items(
                    allCharactersPagingItems.itemCount,
                    key = allCharactersPagingItems.itemKey { it.id }
                ) { index ->
                    allCharactersPagingItems[index]?.let { character ->
                        CharacterItem(
                            character = character,
                            onClick = { viewModel.toggleFavorite(it) }
                        )
                    }
                }
                allCharactersPagingItems.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item {
                                PageLoader(modifier = Modifier.fillParentMaxSize())
                            }
                        }

                        loadState.refresh is LoadState.Error -> {
                            item {
                                ErrorMessage(
                                    modifier = Modifier.fillParentMaxSize(),
                                    message = stringResource(R.string.error_getting_data),
                                    onClickRetry = { retry() })
                            }
                        }

                        loadState.append is LoadState.Loading -> {
                            item {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp)
                                        .wrapContentWidth(Alignment.CenterHorizontally)
                                )
                            }
                        }

                        loadState.append is LoadState.Error -> {
                            item {
                                ErrorMessage(
                                    modifier = Modifier.fillParentMaxSize(),
                                    message = stringResource(R.string.error_getting_data),
                                    onClickRetry = { retry() })
                            }
                        }
                    }
                }
            }
        }
    }
}
