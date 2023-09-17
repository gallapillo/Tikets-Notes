package com.gallapillo.tiketsnotes.domain.use_case.database

data class NoteUseCase (
    val getNotes: GetNotesUseCase,
    val deleteNote: DeleteNoteUseCase,
    val addNote: AddNoteUseCase,
    val updateNote: UpdateNoteUseCase,
    val getNoteById: GetNoteByIdUseCase
)