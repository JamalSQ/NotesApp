package com.example.myapplication.ViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.Repository.NotesRepository
import com.example.myapplication.ViewModel.MainViewModel

class NotesViewModelFactory(private val notesRepository: NotesRepository):ViewModelProvider.Factory {
     override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(notesRepository) as T
    }
}