package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapters.notesAdapter
import com.example.myapplication.DataClasses.Notes_tb
import com.example.myapplication.Repository.NotesRepository
import com.example.myapplication.ViewModel.MainViewModel
import com.example.myapplication.ViewModelFactory.NotesViewModelFactory
import com.example.myapplication.database.NotesDB
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var notesRecyclerView: RecyclerView
    lateinit var mainViewModel: MainViewModel
    lateinit var notesList: ArrayList<Notes_tb>
    lateinit var notesAdapter: notesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize ViewModel
        val notesDao = NotesDB.getDatabase(this).NotestbDao()
        val repository = NotesRepository(notesDao)
        val factory = NotesViewModelFactory(repository)
        mainViewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        // Initialize RecyclerView and adapter
        notesRecyclerView = binding.recyclerviewmain
        notesRecyclerView.layoutManager = LinearLayoutManager(this)
        notesList = ArrayList()
        notesAdapter = notesAdapter(this, notesList, mainViewModel)
        notesRecyclerView.adapter = notesAdapter

        // Observe LiveData from ViewModel
        mainViewModel.getNotes().observe(this, Observer { notes ->
            notesList.clear()
            notesList.addAll(notes)
            notesAdapter.notifyDataSetChanged()
        })

        // Set click listener for adding notes
        binding.addnotes.setOnClickListener {
            val intent = Intent(this, AddNotes_Activity::class.java)
            startActivity(intent)
        }
    }
}
