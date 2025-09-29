package com.example.rickandmortyapp.domain.di

import com.example.rickandmortyapp.domain.usecase.UpdateCharactersFavoriteStatus
import com.example.rickandmortyapp.domain.usecase.UpdateCharactersFavoriteStatusImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindUpdateCharactersFavoriteStatus(
        updateCharactersFavoriteStatus: UpdateCharactersFavoriteStatusImpl
    ): UpdateCharactersFavoriteStatus
}
