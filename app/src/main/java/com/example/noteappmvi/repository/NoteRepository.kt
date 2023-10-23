package com.example.noteappmvi.repository

import com.example.noteappmvi.db.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getAllNotes(): Flow<List<Note>>

    fun getAllFavoriteNotesAdd(): Flow<List<Note>>

    suspend fun deleteAllNotes()

    suspend fun addNote(note: Note)

    suspend fun addFavoriteNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun deleteAllFavoriteNotes()

    suspend fun deleteFavoriteNote(itemId: Int)

}