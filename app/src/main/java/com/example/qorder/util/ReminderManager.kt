package com.example.qorder.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.qorder.data.Note
import com.example.qorder.receiver.ReminderReceiver

class ReminderManager(private val context: Context) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun scheduleReminder(note: Note) {
        if (!note.isReminderSet || note.reminderTime == null) return

        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra(ReminderReceiver.EXTRA_NOTE_ID, note.id)
            putExtra(ReminderReceiver.EXTRA_TITLE, note.title)
            putExtra(ReminderReceiver.EXTRA_CONTENT, note.content)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            note.id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        note.reminderTime,
                        pendingIntent
                    )
                }
            } else {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    note.reminderTime,
                    pendingIntent
                )
            }
        } catch (e: SecurityException) {
            // 没有精确闹钟权限
        }
    }

    fun cancelReminder(noteId: Long) {
        val intent = Intent(context, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            noteId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }
}
