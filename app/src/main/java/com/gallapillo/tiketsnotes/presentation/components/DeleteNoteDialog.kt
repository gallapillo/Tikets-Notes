package com.gallapillo.tiketsnotes.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.gallapillo.tiketsnotes.domain.model.Note

@Composable
fun DeleteNoteDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}


// TODO: REMOVE FROM 1.1.0!
@Composable
fun EditNoteDialog(
    note: Note,
    onDismiss: () -> Unit,
    onSubmit: (String, String) -> Unit
) {
    val text = remember { mutableStateOf(note.text) }
    val name = remember { mutableStateOf(note.name) }

    val oldText = note.text
    val oldName = note.name

    Dialog (
        onDismissRequest = {
            onDismiss()
        },
        properties = DialogProperties(

        )
    ) {
        Box(
            Modifier
                .clip(RectangleShape)
                .fillMaxWidth()
                .height(400.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
           Column {
               Row(
                   horizontalArrangement = Arrangement.Center,
                   modifier = Modifier.fillMaxWidth()
               ) {
                   Text(text = "Edit your own note")
               }
               Spacer(modifier = Modifier.height(16.dp))
               Row(
                   horizontalArrangement = Arrangement.Center,
                   modifier = Modifier.fillMaxWidth()
               ) {
                   OutlinedTextField(
                       value = name.value,
                       onValueChange = {
                           name.value = it
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
                       value = text.value,
                       onValueChange = {
                           text.value = it
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
                       if (oldText != text.value || oldName != name.value)
                        onSubmit(name.value, text.value)
                   }) {
                       Text(text = "Edit Note")
                   }
               }
           }
        }
    }
}