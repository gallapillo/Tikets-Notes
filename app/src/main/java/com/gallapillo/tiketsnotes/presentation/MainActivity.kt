package com.gallapillo.tiketsnotes.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.gallapillo.tiketsnotes.presentation.theme.TiketsNotesTheme
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
                                Button(onClick = { }) {
                                    Text(text = "Create Note")
                                }
                            }
                            // TODO: MAKE YOUR TEXT KSK
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        CategoryChips()
                        NotesLazyGrid()
                    }
                }
            }
        }
    }
}

@Composable
fun NoteCard() {
    Card(
        modifier = Modifier
            .width(220.dp)
            .height(220.dp)
            .padding(12.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column {
            Text(text = "Title", modifier = Modifier.padding(top = 12.dp, start = 8.dp, end = 8.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Body of lorem test lopper prepare", modifier = Modifier.padding(horizontal = 8.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.fillMaxWidth().padding(end = 8.dp),
            ) {
                Icon(Icons.Default.Edit, contentDescription = "Edit Note")
                Icon(Icons.Default.Delete, contentDescription = "Delete Note")
            }
        }
    }
}

@Composable
fun CategoryChips() {
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
fun NotesLazyGrid() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        content = {
            items(4) {
                NoteCard()
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
            NotesLazyGrid()
        }
    }
}