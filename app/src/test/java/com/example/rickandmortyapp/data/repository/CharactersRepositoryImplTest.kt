package com.example.rickandmortyapp.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.rickandmortyapp.data.local.LocalDataSource
import com.example.rickandmortyapp.data.remote.RemoteDataSource
import com.example.rickandmortyapp.domain.model.Character
import com.example.rickandmortyapp.domain.model.Status
import com.example.rickandmortyapp.domain.utils.toCharacter
import com.example.rickandmortyapp.domain.utils.toCharacterEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class CharactersRepositoryImplTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var remoteDataSource: RemoteDataSource

    @Mock
    private lateinit var localDataSource: LocalDataSource

    private lateinit var repository: CharactersRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = CharactersRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun `getFavorites should return list of characters`() = runTest {
        val characterEntity = createCharacter(true).toCharacterEntity()
        val expectedCharacters = listOf(characterEntity.toCharacter())
        `when`(localDataSource.getFavorites()).thenReturn(flowOf(listOf(characterEntity)))

        val result = repository.getFavorites().first()

        Assert.assertEquals(expectedCharacters, result)
    }

    @Test
    fun `addToFavorites should add character to favorites`() = runTest {
        val character = createCharacter(false)
        `when`(localDataSource.addToFavorites(character.toCharacterEntity())).thenReturn(Unit)

        val result = repository.addToFavorites(character)

        assert(result.isSuccess)
    }

    @Test
    fun `removeFromFavorites should remove character from favorites`() = runTest {
        val character = createCharacter(true)
        `when`(localDataSource.removeFromFavorites(character.toCharacterEntity())).thenReturn(Unit)

        val result = repository.removeFromFavorites(character)

        assert(result.isSuccess)
    }

    private fun createCharacter(isFavorite: Boolean) = Character(
        id = 1,
        name = "Rick Sanchez",
        imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
        isFavorite = isFavorite,
        status = Status.ALIVE,
        species = "Human",
        gender = "Male",
        origin = "Earth (C-137)",
        lastLocation = "Citadel of Ricks"
    )
}