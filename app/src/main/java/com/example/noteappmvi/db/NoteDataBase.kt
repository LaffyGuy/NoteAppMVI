package com.example.noteappmvi.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Note::class], version = 2)
abstract class NoteDataBase: RoomDatabase() {

   abstract fun getDao(): NoteDao

   companion object {
       @Volatile
       private var INSTANCE: NoteDataBase? = null


       fun getDb(context: Context): NoteDataBase {

           val tempInstance = INSTANCE
           if(tempInstance != null) return tempInstance

           synchronized(this){
               val instance = Room.databaseBuilder(
                   context,
                   NoteDataBase::class.java,
                   "note.db"
               ).fallbackToDestructiveMigration()
                   .build()

               INSTANCE = instance
               return instance

           }

       }

   }



}