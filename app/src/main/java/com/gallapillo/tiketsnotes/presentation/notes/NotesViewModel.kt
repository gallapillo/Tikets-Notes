package com.gallapillo.tiketsnotes.presentation.notes

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gallapillo.tiketsnotes.domain.model.Note
import com.gallapillo.tiketsnotes.domain.use_case.database.NoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCase: NoteUseCase
) : ViewModel() {
    private val _state: MutableState<NoteState> = mutableStateOf(NoteState.LoadingNotes)
    val state: State<NoteState> = _state

    fun loadAllNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            noteUseCase.getNotes().onEach {  notes ->
                _state.value = NoteState.LoadNotes(notes)
            }.launchIn(viewModelScope)
        }
    }

    fun addNote(name: String, text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val noteToAdd = Note(
                name = name,
                text = text,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )
            noteUseCase.addNote(noteToAdd)
            _state.value = NoteState.AddNote(noteToAdd)
        }
    }

    fun updateNote(name: String, text: String, note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            val updatedNote = Note(
                name = name,
                text = text,
                color = note.color,
                createdAt = note.createdAt,
                updatedAt = System.currentTimeMillis(),
                id = note.id
            )
            noteUseCase.updateNote(updatedNote)
            _state.value = NoteState.UpdateNote(updatedNote)
        }
    }

    fun deleteNote(noteToDelete: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteUseCase.deleteNote(noteToDelete)
            _state.value = NoteState.DeleteNote(noteToDelete)
        }
    }

    fun getNoteById(id: Int) {
        viewModelScope.launch {
            val note = noteUseCase.getNoteById(id)
            _state.value = NoteState.LoadNote(note!!)
        }
    }
}