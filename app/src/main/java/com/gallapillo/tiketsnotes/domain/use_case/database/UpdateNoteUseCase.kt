package com.gallapillo.tiketsnotes.domain.use_case.database

import com.gallapillo.tiketsnotes.data.local.repository.NotesRepository
import com.gallapillo.tiketsnotes.domain.model.Note

class UpdateNoteUseCase (
    private val repository: NotesRepository
) {
    suspend operator fun invoke(note: Note) {
        repository.updateNote(note)
    }
}