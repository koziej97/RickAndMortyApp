package com.example.rickandmortyapp.ui.characters.characterDetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.rickandmortyapp.domain.model.Character
import com.example.rickandmortyapp.domain.model.Status
import com.example.rickandmortyapp.domain.repository.CharactersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterDetailsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var charactersRepository: CharactersRepository

    private lateinit var viewModel: CharacterDetailsViewModel

    private val testDispatcher: TestDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = CharacterDetailsViewModel(charactersRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is Loading`() = runTest {
        val viewState = viewModel.viewState.value
        assertTrue(viewState is CharacterViewState.Loading)
    }

    @Test
    fun `handleIntent LoadCharacter emits Success when repository returns character`() = runTest {
        val characterId = 1
        val character = createCharacter(id = characterId, name = "Rick", isFavorite = false)

        `when`(charactersRepository.getCharacterDataFlow(characterId)).thenReturn(
            flowOf(Result.success(character))
        )

        viewModel.handleIntent(CharacterIntent.LoadCharacter(characterId))
        testDispatcher.scheduler.advanceUntilIdle()

        val viewState = viewModel.viewState.value
        assertTrue(viewState is CharacterViewState.Success)
        assertEquals(character, (viewState as CharacterViewState.Success).character)
    }

    @Test
    fun `handleIntent LoadCharacter emits Error when repository returns failure`() = runTest {
        val characterId = 1

        `when`(charactersRepository.getCharacterDataFlow(characterId)).thenReturn(
            flowOf(Result.failure(Exception("Error loading character")))
        )

        viewModel.handleIntent(CharacterIntent.LoadCharacter(characterId))
        testDispatcher.scheduler.advanceUntilIdle()

        val viewState = viewModel.viewState.value
        assertTrue(viewState is CharacterViewState.Error)
    }

    @Test
    fun `fetchCharacter collects result and updates view state`() = runTest {
        val characterId = 42
        val character = createCharacter(id = characterId, name = "Morty", isFavorite = true)

        `when`(charactersRepository.getCharacterDataFlow(characterId)).thenReturn(
            flowOf(Result.success(character))
        )

        viewModel.handleIntent(CharacterIntent.LoadCharacter(characterId))
        testDispatcher.scheduler.advanceUntilIdle()

        val viewState = viewModel.viewState.value
        assertTrue(viewState is CharacterViewState.Success)
        assertEquals(character, (viewState as CharacterViewState.Success).character)
    }

    private fun createCharacter(
        id: Int = 1,
        name: String = "Rick Sanchez",
        isFavorite: Boolean = false,
    ) = Character(
        id = id,
        name = name,
        imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
        isFavorite = isFavorite,
        status = Status.ALIVE,
        species = "Human",
        gender = "Male",
        origin = "Earth (C-137)",
        lastLocation = "Citadel of Ricks"
    )
}
