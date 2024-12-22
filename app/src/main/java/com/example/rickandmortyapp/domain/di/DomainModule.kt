package com.example.rickandmortyapp.domain.di

import com.example.rickandmortyapp.domain.usecase.UpdateCharactersFavoriteStatus
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    @Singleton
    fun provideUpdateCharactersFavoriteStatus(): UpdateCharactersFavoriteStatus {
        return UpdateCharactersFavoriteStatus()
    }

}