package com.example.noteappmvi.viewmodel.note

import com.example.noteappmvi.db.Note

sealed class NoteViewIntent {
    object LoadNotes: NoteViewIntent()
    data class AddNote(val note: Note): NoteViewIntent()
    data class DeleteNote(val note: Note): NoteViewIntent()
}
