package com.gallapillo.tiketsnotes.presentation

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
            val note = Note(
                name = name,
                text = text,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )
            noteUseCase.addNote(note)
            loadAllNotes()
        }
    }
}