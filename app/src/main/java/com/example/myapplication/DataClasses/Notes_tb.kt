package com.example.myapplication.DataClasses

import android.text.Editable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Notes_tb(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val Title: String,
    val discription: String
)
