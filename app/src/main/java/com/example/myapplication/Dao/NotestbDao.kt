package com.example.myapplication.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.DataClasses.Notes_tb

@Dao
interface NotestbDao {
    @Insert
    suspend fun insertNotes(notesTb: Notes_tb)

    @Query("Select * from Notes_tb")
    fun fetchNotes():LiveData<List<Notes_tb>>

    @Query("DELETE FROM Notes_tb where id= :id")
    fun deleteNotes(id:Int)

    @Query("UPDATE Notes_tb SET Title=:title,discription=:Disc where id=:id")
    suspend fun updateNotes(title:String,Disc:String,id: Int)


}