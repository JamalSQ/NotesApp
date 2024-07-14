package com.example.myapplication.database

import android.content.Context
import android.provider.CalendarContract.Instances
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.Dao.NotestbDao
import com.example.myapplication.DataClasses.Notes_tb

@Database(entities = [Notes_tb::class],version=1, exportSchema = false)
abstract class NotesDB : RoomDatabase() {
    abstract fun NotestbDao():NotestbDao

    companion object{
        @Volatile
        private var INSTANCE:NotesDB?=null
        fun getDatabase(context: Context):NotesDB{
            return INSTANCE ?: synchronized(this){
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    NotesDB::class.java,
                    "Notes_DB"
                ).build()
                INSTANCE=instance
                instance
            }
        }
    }
}