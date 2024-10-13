package com.example.rickandmortyapp.data.di

import android.content.Context
import com.example.rickandmortyapp.data.local.database.CharactersDao
import com.example.rickandmortyapp.data.local.database.CharactersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): CharactersDatabase {
        return CharactersDatabase.getDatabase(context)
    }

    @Provides
    fun provideYourDao(database: CharactersDatabase): CharactersDao {
        return database.charactersDao()
    }

}