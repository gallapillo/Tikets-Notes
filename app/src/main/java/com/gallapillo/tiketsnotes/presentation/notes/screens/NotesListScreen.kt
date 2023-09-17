package com.gallapillo.tiketsnotes.presentation.notes.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.gallapillo.tiketsnotes.domain.model.Note
import com.gallapillo.tiketsnotes.presentation.components.CreateNoteBottomSheet
import com.gallapillo.tiketsnotes.presentation.notes.NoteState
import com.gallapillo.tiketsnotes.presentation.notes.NotesLazyGrid
import com.gallapillo.tiketsnotes.presentation.notes.NotesViewModel
import com.gallapillo.tiketsnotes.presentation.notes.ui_event.NoteUIStateEvent

@Composable
fun NotesListScreen(
    viewModel: NotesViewModel,
    navController: NavController
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val notes = remember { mutableStateOf( mutableListOf<Note>() ) }

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
                notes.value = result.notes.toMutableList()

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
                    notes = notes.value,
                    noteUIStateEvent = NoteUIStateEvent(
                        onDeleteNote = { noteToDelete ->
                            viewModel.deleteNote(noteToDelete)
                        },
                        onUpdateNote = { name, text, noteToUpdate ->
                            viewModel.updateNote(name, text, noteToUpdate)
                        },
                        onClickNote = {note ->
                            navController.navigate("note_detail/${note.id}")
                        }
                    )
                )
            }
            is NoteState.AddNote -> {
                notes.value.add(result.addedNote)
            }
            is NoteState.DeleteNote -> {
                notes.value.remove(result.deletedNote)
            }
            is NoteState.UpdateNote -> {
                notes.value.find { it.id == result.updatedNote.id }?.name =
                    result.updatedNote.name
                notes.value.find { it.id == result.updatedNote.id }?.text =
                    result.updatedNote.text
                notes.value.find { it.id == result.updatedNote.id }?.updatedAt =
                    result.updatedNote.updatedAt
            }
            else -> {}
        }
    }
}