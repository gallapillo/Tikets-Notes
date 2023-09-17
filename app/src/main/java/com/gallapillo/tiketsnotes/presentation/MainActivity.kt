package com.gallapillo.tiketsnotes.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gallapillo.tiketsnotes.presentation.notes.NotesViewModel
import com.gallapillo.tiketsnotes.presentation.notes.screens.NoteDetailScreen
import com.gallapillo.tiketsnotes.presentation.notes.screens.NotesListScreen
import com.gallapillo.tiketsnotes.presentation.theme.TiketsNotesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val viewModel = hiltViewModel<NotesViewModel>()

            TiketsNotesTheme {
                NavHost(
                    navController = navController,
                    startDestination = "notes_list",
                    enterTransition = {
                        EnterTransition.None
                    },
                    exitTransition = {
                        ExitTransition.None
                    }
                ) {
                    composable("notes_list") {
                        NotesListScreen(
                            viewModel = viewModel,
                            navController = navController
                        )
                    }
                    composable("note_detail/{note_id}") {  navBackStackEntry ->
                        val noteId = navBackStackEntry.arguments?.getString("note_id")
                        NoteDetailScreen(
                            viewModel = viewModel,
                            noteId = noteId?.toInt() ?: 0
                        )
                    }
                }
            }
        }
    }
}
