package com.example.qorder.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.qorder.QorderApp
import com.example.qorder.data.Note
import com.example.qorder.data.NoteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NoteRepository = (application as QorderApp).repository

    val allNotes: StateFlow<List<Note>> = repository.allNotes
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun getNoteById(id: Long, onResult: (Note?) -> Unit) {
        viewModelScope.launch {
            onResult(repository.getNoteById(id))
        }
    }

    fun insertNote(title: String, content: String, reminderTime: Long?, callback: ((Long) -> Unit)? = null) {
        viewModelScope.launch {
            val note = Note(
                title = title,
                content = content,
                reminderTime = reminderTime,
                isReminderSet = reminderTime != null
            )
            val id = repository.insert(note)
            callback?.invoke(id)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            repository.update(note.copy(updatedAt = System.currentTimeMillis()))
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.delete(note)
        }
    }

    fun deleteNoteById(id: Long) {
        viewModelScope.launch {
            repository.deleteById(id)
        }
    }
}
