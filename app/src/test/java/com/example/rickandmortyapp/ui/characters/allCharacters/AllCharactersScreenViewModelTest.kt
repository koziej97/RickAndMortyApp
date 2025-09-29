package com.example.rickandmortyapp.ui.characters.allCharacters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.example.rickandmortyapp.domain.model.Character
import com.example.rickandmortyapp.domain.model.Status
import com.example.rickandmortyapp.domain.repository.CharactersRepository
import com.example.rickandmortyapp.domain.usecase.UpdateCharactersFavoriteStatus
import com.example.rickandmortyapp.ui.characters.allCharacters.viewStates.FavoriteCharactersViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any

@OptIn(ExperimentalCoroutinesApi::class)
class AllCharactersScreenViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var charactersRepository: CharactersRepository

    @Mock
    private lateinit var updateCharactersFavoriteStatus: UpdateCharactersFavoriteStatus

    private lateinit var viewModel: AllCharactersScreenViewModel

    private val testDispatcher: TestDispatcher = StandardTestDispatcher()

    private val mockPagingDataFlow = MutableStateFlow(PagingData.empty<Character>())
    private val mockFavoritesFlow = MutableStateFlow(emptyList<Character>())

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        `when`(charactersRepository.getCharactersFlow()).thenReturn(mockPagingDataFlow)
        `when`(charactersRepository.getFavorites()).thenReturn(mockFavoritesFlow)

        `when`(updateCharactersFavoriteStatus.execute(any(), any())).thenReturn(PagingData.empty())

        viewModel = AllCharactersScreenViewModel(
            charactersRepository,
            updateCharactersFavoriteStatus
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial viewState should be loading and not showing favorites`() = runTest {
        assertTrue(viewModel.viewState.value.favoritesState is FavoriteCharactersViewState.Loading)
        assertFalse(viewModel.viewState.value.isShowingFavorites)
    }

    @Test
    fun `LoadCharacters should fetch all and favorite characters`() = runTest {
        val favorites = listOf(createCharacter(1, "Rick", true))
        val allCharacters = mockCharacterList(3)

        viewModel.handleIntent(AllCharactersIntent.LoadCharacters)
        testDispatcher.scheduler.advanceUntilIdle()

        verify(charactersRepository).getFavorites()
        verify(charactersRepository).getCharactersFlow()

        mockFavoritesFlow.value = favorites
        testDispatcher.scheduler.advanceUntilIdle()

        val favoritesState = viewModel.viewState.value.favoritesState
        assertTrue(favoritesState is FavoriteCharactersViewState.Success)
        assertEquals(favorites, (favoritesState as FavoriteCharactersViewState.Success).characters)

        mockPagingDataFlow.value = PagingData.from(allCharacters)
        testDispatcher.scheduler.advanceUntilIdle()

        val viewState = viewModel.viewState.value
        assertTrue(viewState.favoritesState is FavoriteCharactersViewState.Success)
        assertFalse(viewState.isShowingFavorites)
    }

    @Test
    fun `fetchFavorites emits Empty when favorites list is empty`() = runTest {
        viewModel.handleIntent(AllCharactersIntent.LoadCharacters)
        testDispatcher.scheduler.advanceUntilIdle()

        mockFavoritesFlow.value = emptyList()
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.viewState.value.favoritesState is FavoriteCharactersViewState.Empty)
    }

    @Test
    fun `handleIntent ShowFavoritesCharacters sets isShowingFavorites to true`() = runTest {
        viewModel.handleIntent(AllCharactersIntent.ShowFavoritesCharacters)
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.viewState.value.isShowingFavorites)
    }

    @Test
    fun `handleIntent ShowAllCharacters sets isShowingFavorites to false`() = runTest {
        viewModel.handleIntent(AllCharactersIntent.ShowFavoritesCharacters)
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(viewModel.viewState.value.isShowingFavorites)

        viewModel.handleIntent(AllCharactersIntent.ShowAllCharacters)
        testDispatcher.scheduler.advanceUntilIdle()

        assertFalse(viewModel.viewState.value.isShowingFavorites)
    }

    @Test
    fun `handleIntent ToggleFavorite removes from favorites when character is favorite`() = runTest {
        val characterToRemove = createCharacter(id = 5, name = "Summer", isFavorite = true)
        val initialPagingData = PagingData.from(listOf(characterToRemove))

        mockPagingDataFlow.value = initialPagingData
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.handleIntent(AllCharactersIntent.ToggleFavorite(characterToRemove))
        testDispatcher.scheduler.advanceUntilIdle()

        verify(charactersRepository, times(1)).removeFromFavorites(any())
        verify(charactersRepository, times(0)).addToFavorites(any())
    }

    @Test
    fun `handleIntent ToggleFavorite adds to favorites when character is not favorite`() = runTest {
        val characterToAdd = createCharacter(id = 6, name = "Birdperson", isFavorite = false)
        val initialPagingData = PagingData.from(listOf(characterToAdd))

        mockPagingDataFlow.value = initialPagingData
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.handleIntent(AllCharactersIntent.ToggleFavorite(characterToAdd))
        testDispatcher.scheduler.advanceUntilIdle()

        verify(charactersRepository, times(0)).removeFromFavorites(any())
        verify(charactersRepository, times(1)).addToFavorites(any())
    }

    private fun createCharacter(id: Int, name: String, isFavorite: Boolean = false) = Character(
        id = id,
        name = name,
        imageUrl = "url",
        isFavorite = isFavorite,
        status = Status.ALIVE,
        species = "Human",
        gender = "Male",
        origin = "Earth",
        lastLocation = "Location"
    )

    private fun mockCharacterList(count: Int, startIndex: Int = 1): List<Character> {
        return (0 until count).map {
            createCharacter(
                id = startIndex + it,
                name = "Character ${startIndex + it}"
            )
        }
    }
}
