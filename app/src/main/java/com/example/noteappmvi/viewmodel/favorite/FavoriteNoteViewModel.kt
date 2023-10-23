package com.example.noteappmvi.viewmodel.favorite

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

class FavoriteNoteViewModel(private val noteRepository: NoteRepository): ViewModel() {


    var state = MutableStateFlow(NoteViewState<Note>())
        private set

    var job: Job? = null

    init {
        noteIntent(FavoriteNoteViewIntent.LoadNotes)
    }


    fun noteIntent(intent: FavoriteNoteViewIntent){
        when(intent){
            is FavoriteNoteViewIntent.LoadNotes -> {
                getAllFavoriteNotesAdd()
            }
            is  FavoriteNoteViewIntent.AddFavoriteNote -> {
                addFavoriteNotes(intent.note)
            }
            is FavoriteNoteViewIntent.DeleteAllFavoriteNote -> {
                deleteAllFavoriteNotes()
            }
            is FavoriteNoteViewIntent.DeleteFavoriteNote -> {
                deleteFavoriteNote(intent.note.id!!)
            }
        }

    }

    private fun getAllFavoriteNotesAdd(){
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            noteRepository.getAllFavoriteNotesAdd()
                .catch {
                    state.value = state.value.copy(isLoading = false, data = emptyList(), message = it.message.toString())
                }
                .collect{
                    state.value = state.value.copy(isLoading = false, data = it, message = "")
                }
        }
    }



    private fun addFavoriteNotes(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
        try {
            noteRepository.addFavoriteNote(note)
        } catch (e: Exception){
            Log.d("MyTag", "Error - ${e.message.toString()}")
        }

        }
    }


    private fun deleteAllFavoriteNotes(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                noteRepository.deleteAllFavoriteNotes()
            }catch (e: Exception){
                Log.d("MyTag", "Error - ${e.message.toString()}")
            }
        }
    }

    private fun deleteFavoriteNote(itemId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                noteRepository.deleteFavoriteNote(itemId)
            }catch (e: Exception){
                Log.d("MyTag", "Error - ${e.message.toString()}")
            }
        }
    }



}