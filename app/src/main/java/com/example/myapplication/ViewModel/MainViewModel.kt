package com.example.myapplication.ViewModel


import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.DataClasses.Notes_tb
import com.example.myapplication.Repository.NotesRepository
import kotlinx.coroutines.Dispatchers

class MainViewModel(val notesRepository: NotesRepository) : ViewModel(){

    suspend fun insertNotes(notesTb: Notes_tb){
            return notesRepository.insertNotes(notesTb)

    }

    suspend fun updateNotes(title:String,Disc:String,id:Int){
        return notesRepository.UpdateNotes(title,Disc,id)
    }

    fun getNotes():LiveData<List<Notes_tb>>{
        return notesRepository.getNotes()
    }

    suspend fun deleteNotes(id:Int){
        return notesRepository.DeleteNotes(id)
    }

}