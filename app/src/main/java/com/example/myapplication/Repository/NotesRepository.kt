package com.example.myapplication.Repository

import android.icu.text.CaseMap.Title
import androidx.lifecycle.LiveData
import com.example.myapplication.Dao.NotestbDao
import com.example.myapplication.DataClasses.Notes_tb


class NotesRepository(val NotestbDao:NotestbDao) {

    suspend fun insertNotes(Notes_tb:Notes_tb) {
        return NotestbDao.insertNotes(Notes_tb)
    }

    suspend fun UpdateNotes(title:String,Disc:String,id:Int) {
        return NotestbDao.updateNotes(title,Disc,id)
    }

    fun getNotes() : LiveData<List<Notes_tb>> {
        return NotestbDao.fetchNotes()
    }

    suspend fun DeleteNotes(id:Int) {
        return NotestbDao.deleteNotes(id)
    }

}