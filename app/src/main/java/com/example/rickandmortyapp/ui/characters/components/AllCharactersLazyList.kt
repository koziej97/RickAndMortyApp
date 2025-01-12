package com.example.rickandmortyapp.ui.characters.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.domain.model.Character
import com.example.rickandmortyapp.ui.characters.utils.createListOfCharactersForPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun AllCharactersLazyList(
    charactersPagingItems: LazyPagingItems<Character>,
    toggleFavoriteButton: (Character) -> Unit,
    onItemClick: (Int, String) -> Unit
) {
    LazyColumn {
        items(
            charactersPagingItems.itemCount,
            key = charactersPagingItems.itemKey { it.id }
        ) { index ->
            charactersPagingItems[index]?.let { character ->
                CharacterItem(
                    character = character,
                    onFavoriteClick = { toggleFavoriteButton(it) },
                    onItemClick = onItemClick
                )
            }
        }
        charactersPagingItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { PageLoader(modifier = Modifier.fillParentMaxSize()) }
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
                            modifier = Modifier,
                            message = stringResource(R.string.error_getting_data),
                            onClickRetry = { retry() })
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AllCharactersLazyListPreview() {
    val lazyPagingItems: Flow<PagingData<Character>> = flowOf(PagingData.from(
        createListOfCharactersForPreview()
    ))
    AllCharactersLazyList(
        charactersPagingItems = lazyPagingItems.collectAsLazyPagingItems(),
        toggleFavoriteButton = {},
        onItemClick = { _, _ -> }
    )
}
