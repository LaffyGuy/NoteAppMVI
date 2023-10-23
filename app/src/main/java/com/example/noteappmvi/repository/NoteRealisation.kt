package com.example.noteappmvi.repository

import com.example.noteappmvi.db.Note
import com.example.noteappmvi.db.NoteDataBase
import kotlinx.coroutines.flow.Flow

class NoteRealisation(private val db: NoteDataBase): NoteRepository {

    override fun getAllNotes(): Flow<List<Note>> {
        return db.getDao().getAllNotes()
    }

    override suspend fun deleteAllNotes() {
         db.getDao().deleteAllNotes()
    }

    override suspend fun deleteAllFavoriteNotes() {
        db.getDao().deleteAllFavoriteNotes()
    }

    override suspend fun deleteFavoriteNote(itemId: Int) {
        db.getDao().deleteFavoriteNote(itemId)
    }

    override suspend fun addNote(note: Note) {
        db.getDao().addNote(note)
    }

    override suspend fun addFavoriteNote(note: Note) {
        db.getDao().addFavoriteNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        db.getDao().deleteNote(note)
    }

    override fun getAllFavoriteNotesAdd(): Flow<List<Note>> {
        return db.getDao().getAllFavoriteNotesAdd()
    }



}