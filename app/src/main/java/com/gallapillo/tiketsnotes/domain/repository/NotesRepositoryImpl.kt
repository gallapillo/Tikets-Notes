package com.gallapillo.tiketsnotes.domain.repository

import com.gallapillo.tiketsnotes.data.local.NotesDao
import com.gallapillo.tiketsnotes.data.local.repository.NotesRepository
import com.gallapillo.tiketsnotes.domain.model.Note
import kotlinx.coroutines.flow.Flow

class NotesRepositoryImpl(
    private val dao: NotesDao
) : NotesRepository {
    override fun getNotes(): Flow<List<Note>> = dao.getNotes()

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }

    override suspend fun updateNote(note: Note) {
        dao.updateNote(note)
    }

    override suspend fun getNoteById(noteId: Int): Note? {
        return dao.getNoteById(noteId)
    }
}