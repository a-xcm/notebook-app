package com.example.qorder

import android.app.Application
import com.example.qorder.data.NoteDatabase
import com.example.qorder.data.NoteRepository

class QorderApp : Application() {
    val database by lazy { NoteDatabase.getDatabase(this) }
    val repository by lazy { NoteRepository(database.noteDao()) }
}
