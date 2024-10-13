package com.example.rickandmortyapp.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmortyapp.domain.model.Character
import com.example.rickandmortyapp.domain.utils.toCharacter

class CharacterPagingSource(private val remoteDataSource: RemoteDataSource) : PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val page = params.key ?: 1

        return when (val result = remoteDataSource.fetchAllCharacters(page)) {
            else -> {
                if (result.isSuccess) {
                    val charactersResponse = result.getOrNull()
                    val charactersDTO = charactersResponse?.results ?: emptyList()

                    val characters = charactersDTO.map { it.toCharacter() }

                    LoadResult.Page(
                        data = characters,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (charactersResponse?.info?.next != null) page + 1 else null
                    )
                } else {
                    LoadResult.Error(result.exceptionOrNull() ?: Exception("Unknown error"))
                }
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition
    }
}