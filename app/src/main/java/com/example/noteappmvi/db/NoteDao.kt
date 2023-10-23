package com.example.noteappmvi.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {

    @Query("SELECT * FROM note_table")
    fun getAllNotes(): Flow<List<Note>>

    @Query("DELETE FROM note_table")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM note_table WHERE isFavorite = 1")
    fun getAllFavoriteNotesAdd(): Flow<List<Note>>

    @Query("DELETE FROM note_table WHERE isFavorite = 1")
    suspend fun deleteAllFavoriteNotes()

    @Query("DELETE FROM note_table WHERE isFavorite = 1 AND id =:itemId")
    suspend fun deleteFavoriteNote(itemId: Int)

    @Insert
    suspend fun addNote(note: Note)

    @Update
    suspend fun addFavoriteNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)



}