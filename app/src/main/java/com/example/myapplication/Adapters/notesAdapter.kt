package com.example.myapplication.Adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DataClasses.Notes_tb
import com.example.myapplication.R
import com.example.myapplication.ViewModel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class notesAdapter(
    val context: Context,
    val notesList: ArrayList<Notes_tb>,
    private val mainViewModel: MainViewModel
) : RecyclerView.Adapter<notesAdapter.NotesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.notesstructure, parent, false)
        return NotesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val currentNotes = notesList[position]
        holder.title.text = currentNotes.Title
        holder.disc.text = currentNotes.discription

        holder.deleteIcon.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                mainViewModel.deleteNotes(currentNotes.id)
            }
            notesList.removeAt(position)
            notifyItemRemoved(position)
        }

        holder.editIcon.setOnClickListener {
            showEditDialog(currentNotes)
        }
    }

    private fun showEditDialog(currentNotes: Notes_tb) {
        val dialogview=LayoutInflater.from(context).inflate(R.layout.dialog_edit_note,null)
        val utitle=dialogview.findViewById<EditText>(R.id.UpdateTitle)
        val uDisc=dialogview.findViewById<EditText>(R.id.UpdateDescription)
        val Ubotton=dialogview.findViewById<Button>(R.id.updateNoteButton)

        utitle.setText(currentNotes.Title)
        uDisc.setText(currentNotes.discription)

        val dialog = AlertDialog.Builder(context)
            .setView(dialogview)
            .create()

        Ubotton.setOnClickListener {
            val Ntitle=utitle.text.toString()
            val Ndisc=uDisc.text.toString()

            if(Ntitle.isNotEmpty() || Ndisc.isNotEmpty()){
                CoroutineScope(Dispatchers.IO).launch {
                    mainViewModel.updateNotes(Ntitle,Ndisc,currentNotes.id)
                }
                dialog.dismiss()
            }else{
                Toast.makeText(context, "The fields can't be Empty", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()
    }
    class NotesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.TV_Title)
        val disc: TextView = view.findViewById(R.id.TV_Discription)
        val deleteIcon: ImageView = view.findViewById(R.id.deleteNotes)
        val editIcon: ImageView = view.findViewById(R.id.editNotes)
    }
}
