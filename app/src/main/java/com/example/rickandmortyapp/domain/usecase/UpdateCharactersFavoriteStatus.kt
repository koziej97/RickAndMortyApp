package com.example.rickandmortyapp.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.example.rickandmortyapp.domain.model.Character
import javax.inject.Inject

interface UpdateCharactersFavoriteStatus {

    fun execute(
        pagingData: PagingData<Character>,
        favoriteList: List<Character>
    ): PagingData<Character>
}

class UpdateCharactersFavoriteStatusImpl @Inject constructor(): UpdateCharactersFavoriteStatus {

    override fun execute(
        pagingData: PagingData<Character>,
        favoriteList: List<Character>
    ): PagingData<Character> {
        return pagingData.map { character ->
            character.copy(isFavorite = favoriteList.any { it.id == character.id })
        }
    }
}
