package com.gallapillo.tiketsnotes.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.gallapillo.tiketsnotes.presentation.components.CreateNoteBottomSheet
import com.gallapillo.tiketsnotes.presentation.notes.NoteState
import com.gallapillo.tiketsnotes.presentation.notes.NotesLazyGrid
import com.gallapillo.tiketsnotes.presentation.notes.NotesViewModel
import com.gallapillo.tiketsnotes.presentation.theme.TiketsNotesTheme
import com.gallapillo.tiketsnotes.presentation.ui_event.NoteUIStateEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var showBottomSheet by remember { mutableStateOf(false) }

            val viewModel = hiltViewModel<NotesViewModel>()

            TiketsNotesTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background,
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                showBottomSheet = true
                            }
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Add a note")
                        }
                    }
                ) { paddingValues ->
                    viewModel.loadAllNotes()
                    when (val result = viewModel.state.value) {
                        is NoteState.LoadNotes -> {
                            if (showBottomSheet) {
                                CreateNoteBottomSheet(
                                    onDismiss = {
                                        showBottomSheet = false
                                    },
                                    onCreateNote = { name, text ->
                                        viewModel.addNote(name = name, text = text)
                                        showBottomSheet = false
                                    }
                                )
                            }

                            NotesLazyGrid(
                                modifier = Modifier.fillMaxSize().padding(paddingValues),
                                notes = result.notes,
                                noteUIStateEvent = NoteUIStateEvent(
                                    onDeleteNote = { noteToDelete ->
                                        viewModel.deleteNote(noteToDelete)
                                    },
                                    onUpdateNote = { name, text, noteToUpdate ->
                                        viewModel.updateNote(name, text, noteToUpdate)
                                    }
                                )
                            )
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}
