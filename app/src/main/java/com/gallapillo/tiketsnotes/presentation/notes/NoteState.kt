package com.gallapillo.tiketsnotes.presentation.notes

import com.gallapillo.tiketsnotes.domain.model.Note

sealed class NoteState {
    object LoadingNotes : NoteState()

    class LoadNotes(val notes: List<Note>) : NoteState()

    class AddNote(val addedNote: Note) : NoteState()

    class UpdateNote(val updatedNote: Note) : NoteState()

    class DeleteNote(val deletedNote: Note) : NoteState()

    class LoadNote(val note: Note) : NoteState()
}
