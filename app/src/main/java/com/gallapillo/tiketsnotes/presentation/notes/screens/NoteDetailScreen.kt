package com.gallapillo.tiketsnotes.presentation.notes.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gallapillo.tiketsnotes.presentation.notes.NoteState
import com.gallapillo.tiketsnotes.presentation.notes.NotesViewModel
import com.gallapillo.tiketsnotes.presentation.theme.noteColors

@Composable
fun NoteDetailScreen(
    viewModel: NotesViewModel,
    noteId: Int
) {
    viewModel.getNoteById(noteId)
    when (val result = viewModel.state.value) {
        is NoteState.LoadNote -> {
            Column(modifier = Modifier.fillMaxSize().background(noteColors[result.note.color])) {
                Row {
                    Text(text = result.note.name, fontSize = 24.sp, modifier = Modifier.padding(16.dp))
                }
                Row {
                    Text(text = result.note.text, modifier = Modifier.padding(16.dp))
                }
            }
        }
        else -> {}
    }

}