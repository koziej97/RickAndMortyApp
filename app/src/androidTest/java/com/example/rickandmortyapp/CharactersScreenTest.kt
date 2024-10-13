package com.example.rickandmortyapp

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CharactersScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testChangingTheme() {
        composeTestRule.onNodeWithText("Rick and Morty Characters").assertIsDisplayed()

        val isDarkMode = runCatching {
            composeTestRule.onNodeWithTag("Moon Icon").assertExists()
            true
        }.getOrDefault(false)

        if (isDarkMode) {
            composeTestRule.onNodeWithContentDescription("Sun Icon").assertExists()
            composeTestRule.onNodeWithContentDescription("Sun Icon").performClick()
            composeTestRule.onNodeWithContentDescription("Moon Icon").assertExists()
        } else {
            composeTestRule.onNodeWithContentDescription("Moon Icon").assertExists()
            composeTestRule.onNodeWithContentDescription("Moon Icon").performClick()
            composeTestRule.onNodeWithContentDescription("Sun Icon").assertExists()
        }
    }

    @Test
    fun testFavoriteCharacterToggle() {
        composeTestRule.onNodeWithText("Rick and Morty Characters").assertExists()

        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithText("Rick Sanchez").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithTag("FavoriteIcon_Rick Sanchez", useUnmergedTree = true)
            .assert(hasContentDescription("Not Favorite"))

        composeTestRule.onNodeWithTag("FavoriteIcon_Rick Sanchez", useUnmergedTree = true)
            .performClick()

        composeTestRule.onNodeWithTag("FavoriteIcon_Rick Sanchez", useUnmergedTree = true)
            .assert(hasContentDescription("Favorite"))

        composeTestRule.onNodeWithTag("FavoriteIcon_Rick Sanchez", useUnmergedTree = true)
            .performClick()

        composeTestRule.onNodeWithTag("FavoriteIcon_Rick Sanchez", useUnmergedTree = true)
            .assert(hasContentDescription("Not Favorite"))
    }

    @Test
    fun testFavoriteList() {
        composeTestRule.onNodeWithText("Rick and Morty Characters").assertExists()

        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithText("Rick Sanchez").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithTag("FavoriteIcon_Rick Sanchez", useUnmergedTree = true)
            .assert(hasContentDescription("Not Favorite"))

        composeTestRule.onNodeWithTag("FavoriteIcon_Rick Sanchez", useUnmergedTree = true)
            .performClick()

        composeTestRule.onNodeWithText("Favorite Characters", useUnmergedTree = true)
            .performClick()

        composeTestRule.onNodeWithText("Rick Sanchez").assertExists()
    }
}
