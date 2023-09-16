package com.gallapillo.tiketsnotes.domain.use_case.database

import com.gallapillo.tiketsnotes.data.local.repository.NotesRepository
import com.gallapillo.tiketsnotes.domain.model.Note
import java.io.InvalidClassException

class AddNoteUseCase(
    private val repository: NotesRepository
) {
    @Throws(InvalidClassException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidClassException("The title is empty")
        }
        if (note.text.isBlank()) {
            throw InvalidClassException("The text is empty")
        }
        repository.insertNote(note)
    }
}