package com.gallapillo.tiketsnotes.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gallapillo.tiketsnotes.domain.model.Note
import com.gallapillo.tiketsnotes.presentation.components.DeleteNoteDialog
import com.gallapillo.tiketsnotes.presentation.components.EditNoteDialog
import com.gallapillo.tiketsnotes.presentation.theme.TiketsNotesTheme
import com.gallapillo.tiketsnotes.presentation.theme.noteColors
import com.gallapillo.tiketsnotes.presentation.ui_event.NoteUIStateEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val sheetState = rememberModalBottomSheetState()
            var showBottomSheet by remember { mutableStateOf(false) }
            var name by rememberSaveable { mutableStateOf("") }
            var text by rememberSaveable { mutableStateOf("") }

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
                                ModalBottomSheet(
                                    onDismissRequest = {
                                        showBottomSheet = false
                                    },
                                    sheetState = sheetState,
                                    modifier = Modifier.height(600.dp)
                                ) {
                                    // Sheet content
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(text = "Create your own note")
                                    }
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        OutlinedTextField(
                                            value = name,
                                            onValueChange = {
                                                name = it
                                            },
                                            label = { Text("Name") }
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        OutlinedTextField(
                                            value = text,
                                            onValueChange = {
                                                text = it
                                            },
                                            label = { Text("Text") }
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Button(onClick = {
                                            if (name.isNotEmpty() && text.isNotEmpty()) {
                                                viewModel.addNote(name = name, text = text)
                                                name = ""
                                                text = ""
                                                showBottomSheet = false
                                            } else
                                                Toast.makeText(
                                                    this@MainActivity,
                                                    "Empty text or name!",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                        }) {
                                            Text(text = "Create Note")
                                        }
                                    }
                                }
                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(paddingValues)
                            ) {
                                NotesLazyGrid(
                                    result.notes,
                                    NoteUIStateEvent(
                                        onDeleteNote = { noteToDelete ->

                                        },
                                        onUpdateNote = { name, text, noteToUpdate ->
                                            viewModel.updateNote(name, text, noteToUpdate)
                                        }
                                    )
                                )
                            }
                        }
                        else -> {}
                    }
                }
            }
        }
    }
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
            .padding(12.dp),
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

@Composable
fun CategoryChips() {
    // TODO: SAVE HERE MAYBE RELEASE IN 1.1.0!
    LazyRow(content = {
        items(10) {
            Card(modifier = Modifier
                .padding(12.dp)
                .height(32.dp)
            ) {
                Text(text = "Simple Category", modifier = Modifier.padding(4.dp))
            }
        }
    })
}

@Composable
fun NotesLazyGrid(
    notes: List<Note>,
    noteUIStateEvent: NoteUIStateEvent = NoteUIStateEvent()
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        content = {
            items(notes.size) { notesKey ->
                NoteCard(notes[notesKey], noteUIStateEvent)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TiketsNotesTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            CategoryChips()
            NotesLazyGrid(listOf())
        }
    }
}