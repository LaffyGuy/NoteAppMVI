package com.example.noteappmvi.viewmodel

//sealed class NoteViewState {
//    object Loading: NoteViewState()
//    data class Success(val notes: List<Note>): NoteViewState()
//    data class Error(val message: String): NoteViewState()
//}

data class NoteViewState<T>(
    val isLoading : Boolean = true,
    val data: List<T> = emptyList(),
    val message: String = ""
)


