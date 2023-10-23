package com.example.noteappmvi.viewmodel.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.noteappmvi.repository.NoteRepository
import com.example.noteappmvi.viewmodel.favorite.FavoriteNoteViewModel
import com.example.noteappmvi.viewmodel.note.NoteViewModel

class NoteViewModelFactory(private val noteRepository: NoteRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass){
            NoteViewModel::class.java -> {
                NoteViewModel(noteRepository)
            }
            FavoriteNoteViewModel::class.java -> {
                FavoriteNoteViewModel(noteRepository)
            }
            else -> Unit
        }
        return viewModel as T
    }
}