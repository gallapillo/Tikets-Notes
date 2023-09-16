package com.gallapillo.tiketsnotes.presentation.notes

import com.gallapillo.tiketsnotes.domain.model.Note

sealed class NoteState {
    object LoadingNotes : NoteState()

    class LoadNotes(val notes: List<Note>) : NoteState()
}
