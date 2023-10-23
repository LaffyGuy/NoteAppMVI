package com.example.noteappmvi.viewmodel.favorite

import com.example.noteappmvi.db.Note

sealed class FavoriteNoteViewIntent {
    object LoadNotes: FavoriteNoteViewIntent()
    data class AddFavoriteNote(val note: Note): FavoriteNoteViewIntent()
    data class DeleteFavoriteNote(val note: Note): FavoriteNoteViewIntent()
    object DeleteAllFavoriteNote: FavoriteNoteViewIntent()
}