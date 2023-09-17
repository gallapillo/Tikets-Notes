package com.gallapillo.tiketsnotes.data.local

import androidx.room.*
import com.gallapillo.tiketsnotes.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("SELECT * FROM ${Note.TABLE_NAME}")
    fun getNotes(): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Query("SELECT * FROM ${Note.TABLE_NAME} WHERE id = :noteId")
    suspend fun getNoteById(noteId: Int): Note?
}