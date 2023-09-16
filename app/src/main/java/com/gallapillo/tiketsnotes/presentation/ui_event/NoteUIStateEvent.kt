package com.gallapillo.tiketsnotes.presentation.ui_event

import com.gallapillo.tiketsnotes.domain.model.Note

data class NoteUIStateEvent(
    val onDeleteNote: (Note) -> Unit = {},
    val onUpdateNote: (String, String, Note) -> Unit = {_, _, _ ->}
)