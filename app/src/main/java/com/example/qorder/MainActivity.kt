package com.example.qorder

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.qorder.data.Note
import com.example.qorder.ui.NoteEditScreen
import com.example.qorder.ui.NoteListScreen
import com.example.qorder.ui.theme.QorderTheme
import com.example.qorder.util.ReminderManager
import com.example.qorder.viewmodel.NoteViewModel

class MainActivity : ComponentActivity() {
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { _ -> }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestNotificationPermission()

        setContent {
            QorderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    QorderApp()
                }
            }
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}

@Composable
fun QorderApp(viewModel: NoteViewModel = viewModel()) {
    val navController = rememberNavController()
    val notes by viewModel.allNotes.collectAsState()
    val reminderManager = remember { ReminderManager(androidx.compose.ui.platform.LocalContext.current) }

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            NoteListScreen(
                notes = notes,
                onAddNote = { navController.navigate("edit/-1") },
                onNoteClick = { noteId -> navController.navigate("edit/$noteId") },
                onDeleteNote = { note ->
                    reminderManager.cancelReminder(note.id)
                    viewModel.deleteNote(note)
                }
            )
        }

        composable("edit/{noteId}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")?.toLongOrNull() ?: -1L

            var note by remember { mutableStateOf<Note?>(null) }
            var isLoading by remember { mutableStateOf(true) }

            LaunchedEffect(noteId) {
                if (noteId >= 0) {
                    viewModel.getNoteById(noteId) { loadedNote ->
                        note = loadedNote
                        isLoading = false
                    }
                } else {
                    isLoading = false
                }
            }

            if (!isLoading) {
                NoteEditScreen(
                    note = note,
                    onSave = { title, content, reminderTime ->
                        if (noteId >= 0 && note != null) {
                            val updatedNote = note!!.copy(
                                title = title,
                                content = content,
                                reminderTime = reminderTime,
                                isReminderSet = reminderTime != null
                            )
                            viewModel.updateNote(updatedNote)
                            if (reminderTime != null) {
                                reminderManager.scheduleReminder(updatedNote)
                            } else {
                                reminderManager.cancelReminder(noteId)
                            }
                        } else {
                            viewModel.insertNote(title, content, reminderTime) { newId ->
                                if (reminderTime != null) {
                                    val newNote = Note(
                                        id = newId,
                                        title = title,
                                        content = content,
                                        reminderTime = reminderTime,
                                        isReminderSet = true
                                    )
                                    reminderManager.scheduleReminder(newNote)
                                }
                            }
                        }
                        navController.popBackStack()
                    },
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
