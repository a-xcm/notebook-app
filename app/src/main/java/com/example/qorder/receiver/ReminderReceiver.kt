package com.example.qorder.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.qorder.MainActivity
import com.example.qorder.R

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val noteId = intent.getLongExtra(EXTRA_NOTE_ID, -1)
        val title = intent.getStringExtra(EXTRA_TITLE) ?: "备忘录提醒"
        val content = intent.getStringExtra(EXTRA_CONTENT) ?: ""

        createNotificationChannel(context)
        showNotification(context, noteId, title, content)
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "备忘录提醒",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "备忘录定时提醒通知"
            }
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification(context: Context, noteId: Long, title: String, content: String) {
        val openIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("note_id", noteId)
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            noteId.toInt(),
            openIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        try {
            NotificationManagerCompat.from(context).notify(noteId.toInt(), notification)
        } catch (e: SecurityException) {
            // 没有通知权限
        }
    }

    companion object {
        const val CHANNEL_ID = "reminder_channel"
        const val EXTRA_NOTE_ID = "extra_note_id"
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_CONTENT = "extra_content"
    }
}
