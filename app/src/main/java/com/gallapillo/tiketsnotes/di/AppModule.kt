package com.gallapillo.tiketsnotes.di

import android.app.Application
import androidx.room.Room
import com.gallapillo.tiketsnotes.data.local.NotesDatabase
import com.gallapillo.tiketsnotes.data.local.repository.NotesRepository
import com.gallapillo.tiketsnotes.domain.repository.NotesRepositoryImpl
import com.gallapillo.tiketsnotes.domain.use_case.database.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNotesDatabase(app: Application): NotesDatabase {
        return Room.databaseBuilder(
            app,
            NotesDatabase::class.java,
            NotesDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNotesRepository(db: NotesDatabase): NotesRepository {
        return NotesRepositoryImpl(db.notesDao)
    }

    @Provides
    @Singleton
    fun provideTodoUseCase(repository: NotesRepository): NoteUseCase {
        return NoteUseCase(
            getNotes = GetNotesUseCase(repository),
            updateNote = UpdateNoteUseCase(repository),
            deleteNote = DeleteNoteUseCase(repository),
            addNote = AddNoteUseCase(repository),
            getNoteById = GetNoteByIdUseCase(repository)
        )
    }
}