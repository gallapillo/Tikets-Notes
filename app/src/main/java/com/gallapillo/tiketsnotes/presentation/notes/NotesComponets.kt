package com.gallapillo.tiketsnotes.presentation.notes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gallapillo.tiketsnotes.domain.model.Note
import com.gallapillo.tiketsnotes.presentation.components.DeleteNoteDialog
import com.gallapillo.tiketsnotes.presentation.components.EditNoteDialog
import com.gallapillo.tiketsnotes.presentation.theme.TiketsNotesTheme
import com.gallapillo.tiketsnotes.presentation.theme.noteColors
import com.gallapillo.tiketsnotes.presentation.notes.ui_event.NoteUIStateEvent

@Preview(showBackground = true)
@Composable
fun NoteCardPreview() {
    NoteCard(
        note = Note(
            id = 0,
            text = "Text of Note",
            name = "Name Note",
            color = 5,
            createdAt = 1959145,
            updatedAt = 1959145
        )
    )
}

@Preview(showBackground = true)
@Composable
fun NotesLazyGridPreview() {
    TiketsNotesTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            NotesLazyGrid(listOf(
                Note(
                    id = 0,
                    text = "Text of Note",
                    name = "Name Note",
                    color = 1,
                    createdAt = 1959145,
                    updatedAt = 1959145
                ),
                Note(
                    id = 1,
                    text = "Text of Note",
                    name = "Name Note",
                    color = 10,
                    createdAt = 1959145,
                    updatedAt = 1959145
                ),
                Note(
                    id = 2,
                    text = "Text of Note",
                    name = "Name Note",
                    color = 3,
                    createdAt = 1959145,
                    updatedAt = 1959145
                ),
                Note(
                    id = 3,
                    text = "Text of Note",
                    name = "Name Note",
                    color = 6,
                    createdAt = 1959145,
                    updatedAt = 1959145
                ),
                Note(
                    id = 4,
                    text = "Text of Note",
                    name = "Name Note",
                    color = 8,
                    createdAt = 1959145,
                    updatedAt = 1959145
                ),
            ))
        }
    }
}

@Composable
fun NotesLazyGrid(
    notes: List<Note>,
    noteUIStateEvent: NoteUIStateEvent = NoteUIStateEvent(),
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        content = {
            items(notes.size) { notesKey ->
                NoteCard(notes[notesKey], noteUIStateEvent)
            }
        }
    )
}

@Composable
fun NoteCard(
    note: Note,
    noteUIStateEvent: NoteUIStateEvent = NoteUIStateEvent()
) {
    val openDeleteNoteDialog = remember { mutableStateOf(false) }
    val openEditNoteDialog = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .width(220.dp)
            .height(220.dp)
            .padding(12.dp)
            .clickable {
                noteUIStateEvent.onClickNote(note)
            },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = noteColors[note.color],
        )
    ) {
        Column {
            Text(text = note.name, modifier = Modifier.padding(top = 12.dp, start = 8.dp, end = 8.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = note.text, modifier = Modifier.padding(horizontal = 8.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
            ) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "Edit Note",
                    modifier = Modifier.clickable {
                        openEditNoteDialog.value = true
                    }
                )
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete Note",
                    modifier = Modifier.clickable {
                        openDeleteNoteDialog.value = true
                    }
                )
                if (openDeleteNoteDialog.value) {
                    DeleteNoteDialog(
                        onDismissRequest = { openDeleteNoteDialog.value = false },
                        onConfirmation = {
                            noteUIStateEvent.onDeleteNote(note)
                            openDeleteNoteDialog.value = false
                        },
                        dialogTitle = "Delete Note",
                        dialogText = "Are you sure to remove note!",
                        icon = Icons.Default.Delete
                    )
                }
                if (openEditNoteDialog.value) {
                    EditNoteDialog(
                        note = note,
                        onSubmit = { updatedName, updatedText ->
                            noteUIStateEvent.onUpdateNote(
                                updatedName, updatedText, note
                            )
                            openEditNoteDialog.value = false
                        },
                        onDismiss = {
                            openEditNoteDialog.value = false
                        }
                    )
                }
            }
        }
    }
}