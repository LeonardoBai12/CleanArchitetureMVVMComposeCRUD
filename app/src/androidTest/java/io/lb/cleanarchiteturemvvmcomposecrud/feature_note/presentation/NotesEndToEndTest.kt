package io.lb.cleanarchiteturemvvmcomposecrud.feature_note.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.lb.cleanarchiteturemvvmcomposecrud.feature_note.core.util.TestTags
import io.lb.cleanarchiteturemvvmcomposecrud.feature_note.di.AppModule
import io.lb.cleanarchiteturemvvmcomposecrud.feature_note.presentation.add_edit_note.components.AddEditNoteScreen
import io.lb.cleanarchiteturemvvmcomposecrud.feature_note.presentation.notes.NotesScreen
import io.lb.cleanarchiteturemvvmcomposecrud.feature_note.presentation.util.Screen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesEndToEndTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @ExperimentalAnimationApi
    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = Screen.NotesScreen.route
            ) {
                composable(route = Screen.NotesScreen.route) {
                    NotesScreen(navController = navController)
                }
                composable(
                    route = Screen.AddEditNoteScreen.route +
                            "?noteId={noteId}&noteColor={noteColor}",
                    arguments = listOf(
                        navArgument(
                            name = "noteId"
                        ) {
                            type = NavType.IntType
                            defaultValue = -1
                        },
                        navArgument(
                            name = "noteColor"
                        ) {
                            type = NavType.IntType
                            defaultValue = -1
                        },
                    )
                ) {
                    val color = it.arguments?.getInt("noteColor") ?: -1
                    AddEditNoteScreen(
                        navController = navController,
                        noteColor = color
                    )
                }
            }
        }
    }

    @Test
    fun saveNewNote_editAfterwards() {
        // Click on FAB to get to add note screen
        composeRule.onNodeWithContentDescription("Add").performClick()

        // Enter texts in title and content text fields
        composeRule
            .onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .performTextInput("test-title")
        composeRule
            .onNodeWithTag(TestTags.CONTENT_TEXT_FIELD)
            .performTextInput("test-content")

        // Save the note
        composeRule
            .onNodeWithContentDescription("Save")
            .performClick()

        // Make sure there is a note in the list  with our title and content
        composeRule
            .onNodeWithText("test-title")
            .assertIsDisplayed()

        // Click on the note do edit it
        composeRule
            .onNodeWithText("test-title")
            .performClick()

        // Make sure title and content text fields contain note title and content
        composeRule
            .onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .assertTextEquals("test-title")
        composeRule
            .onNodeWithTag(TestTags.CONTENT_TEXT_FIELD)
            .assertTextEquals("test-content")

        // Add the text "2" to the title text field
        composeRule
            .onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .performTextInput("2")

        // Save the note
        composeRule
            .onNodeWithContentDescription("Save")
            .performClick()

        // Make sure the update was applied to the list
        composeRule
            .onNodeWithTag("2")
            .assertIsDisplayed()
    }

    @Test
    fun saveNewNotes_orderByTitlteDescending() {
        for (i in 1..3 ) {
            // Click on FAB to get to add note screen
            composeRule.onNodeWithContentDescription("Add").performClick()

            // Enter texts in title and content text fields
            composeRule
                .onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
                .performTextInput("test-title$i")
            composeRule
                .onNodeWithTag(TestTags.CONTENT_TEXT_FIELD)
                .performTextInput("test-content$i")

            // Save the note
            composeRule
                .onNodeWithContentDescription("Save")
                .performClick()
        }

        composeRule
            .onNodeWithText("test-title1")
            .assertIsDisplayed()
        composeRule
            .onNodeWithText("test-title2")
            .assertIsDisplayed()
        composeRule
            .onNodeWithText("test-title3")
            .assertIsDisplayed()

        composeRule
            .onNodeWithContentDescription("Sort")
            .performClick()
        composeRule
            .onNodeWithContentDescription("Title")
            .performClick()
        composeRule
            .onNodeWithContentDescription("Descending")
            .performClick()

        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[0]
            .assertTextEquals("test-title1")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[0]
            .assertTextEquals("test-title2")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[0]
            .assertTextEquals("test-title3")
    }
}