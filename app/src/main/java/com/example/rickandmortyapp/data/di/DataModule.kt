package com.example.rickandmortyapp.data.di

import com.example.rickandmortyapp.data.remote.ApiService
import com.example.rickandmortyapp.data.remote.RemoteDataSource
import com.example.rickandmortyapp.data.remote.RemoteDataSourceImpl
import com.example.rickandmortyapp.data.repository.CharactersRepositoryImpl
import com.example.rickandmortyapp.domain.repository.CharactersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: ApiService): RemoteDataSource {
        return RemoteDataSourceImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideCharactersRepository(
        remoteDataSource: RemoteDataSource
    ): CharactersRepository {
        return CharactersRepositoryImpl(remoteDataSource)
    }
}