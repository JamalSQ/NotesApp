package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.DataClasses.Notes_tb
import com.example.myapplication.Repository.NotesRepository
import com.example.myapplication.ViewModel.MainViewModel
import com.example.myapplication.ViewModelFactory.NotesViewModelFactory
import com.example.myapplication.database.NotesDB
import com.example.myapplication.databinding.ActivityAddNotesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddNotes_Activity : AppCompatActivity() {
    lateinit var binding: ActivityAddNotesBinding
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddNotesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize the ViewModel
        val dao = NotesDB.getDatabase(this).NotestbDao()
        val repository = NotesRepository(dao)
        val factory = NotesViewModelFactory(repository)
        mainViewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        // Handle Add Notes Submit Button Click
        binding.AddNotesSubmitBtn.setOnClickListener {
            val title = binding.edttitle.text.toString()
            val disc = binding.edtdisc.text.toString()
            if (title.isEmpty() || disc.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            } else {
                val newNote = Notes_tb(0, title, disc)
                // Launch a coroutine to insert the note
                CoroutineScope(Dispatchers.IO).launch {
                    mainViewModel.insertNotes(newNote)
                    // Switch to the main thread for UI updates
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@AddNotes_Activity, "Data inserted successfully", Toast.LENGTH_SHORT).show()
                        // Navigate back to MainActivity
                        val intent = Intent(this@AddNotes_Activity, MainActivity::class.java)
                        startActivity(intent)
                        finish() // Close the current activity
                    }
                }
            }
        }
    }
}
