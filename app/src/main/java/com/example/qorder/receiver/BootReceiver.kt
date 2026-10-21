package com.example.qorder.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.qorder.QorderApp
import com.example.qorder.data.Note
import com.example.qorder.util.ReminderManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val app = context.applicationContext as? QorderApp ?: return
            val reminderManager = ReminderManager(context)

            CoroutineScope(Dispatchers.IO).launch {
                val notesWithReminders = app.repository.getAllNotesWithReminders()
                val currentTime = System.currentTimeMillis()

                for (note in notesWithReminders) {
                    if (note.isReminderSet && note.reminderTime != null && note.reminderTime > currentTime) {
                        reminderManager.scheduleReminder(note)
                    }
                }
            }
        }
    }
}
