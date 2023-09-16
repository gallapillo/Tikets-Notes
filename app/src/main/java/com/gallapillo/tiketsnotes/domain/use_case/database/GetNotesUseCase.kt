package com.gallapillo.tiketsnotes.domain.use_case.database

import com.gallapillo.tiketsnotes.data.local.repository.NotesRepository
import com.gallapillo.tiketsnotes.domain.model.Note
import kotlinx.coroutines.flow.Flow

class GetNotesUseCase(
    private val repository: NotesRepository
) {
    operator fun invoke(): Flow<List<Note>> {
        return repository.getNotes()
    }
}