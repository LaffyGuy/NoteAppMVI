package com.example.noteappmvi.viewmodel.note

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteappmvi.db.Note
import com.example.noteappmvi.repository.NoteRepository
import com.example.noteappmvi.viewmodel.NoteViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.lang.Exception

class NoteViewModel(private val noteRepository: NoteRepository): ViewModel() {


    var state = MutableStateFlow(NoteViewState<Note>())
        private set

    private var job: Job? = null

    init {
        noteIntent(NoteViewIntent.LoadNotes)
    }

    fun noteIntent(intent: NoteViewIntent){
        when(intent){
            is NoteViewIntent.LoadNotes -> {
                getAllNotes()
            }
            is NoteViewIntent.AddNote -> {
                addNote(intent.note)
            }
            is NoteViewIntent.DeleteNote -> {
                deleteNote(intent.note)
            }
        }
    }


    private fun getAllNotes() {
        job?.cancel()
        job = viewModelScope.launch {
            noteRepository.getAllNotes()
                .catch {
                    state.value = state.value.copy(isLoading = false, data = emptyList(), message = it.message.toString())
                }
                .collect{
                    state.value = state.value.copy(isLoading = false, data = it, message = "")
                }
        }

    }

    private fun addNote(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                noteRepository.addNote(note)
            }catch (e: Exception){
                Log.d("MyTag", "Error - ${e.message.toString()}")
            }
        }
    }

    private fun deleteNote(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                noteRepository.deleteNote(note)
            }catch (e: Exception){
                Log.d("MyTag", "Error - ${e.message.toString()}")
            }
        }
    }

}