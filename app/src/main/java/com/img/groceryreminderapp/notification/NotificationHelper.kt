package com.img.groceryreminderapp.notification

import android.R.attr.action
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.img.groceryreminderapp.MainActivity
import com.img.groceryreminderapp.R
import kotlin.jvm.java

class NotificationHelper(private val context: Context) {

    companion object {
        const val CHANNEL_ID = "reminder_channel"
        const val CHANNEL_NAME = "Store Reminders"
        const val ACTION_MARK_DONE = "com.example.k2stachackthon.ACTION_MARK_DONE"
        const val EXTRA_REMINDER_ID = "reminder_id"
        const val EXTRA_ITEM_NAME = "item_name"

        private val MESSAGES = listOf(
            { item: String -> "Hey 👋 Looks like you're at the store. Don't forget $item 🛒" },
            { item: String -> "Perfect timing! You're near your grocery store. Grab $item." },
            { item: String -> "You're here 🎉 $item is waiting for you." }
        )
    }

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifies when you arrive at a saved store location"
                enableVibration(true)
            }
            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    fun showReminderNotification(reminderId: Int, itemName: String) {
        val message = MESSAGES.random().invoke(itemName)

        val markDoneIntent = Intent(context, MarkDoneReceiver::class.java).apply {
            action = ACTION_MARK_DONE
            putExtra(EXTRA_REMINDER_ID, reminderId)
        }
        val markDonePendingIntent = PendingIntent.getBroadcast(
            context,
            reminderId,
            markDoneIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val openAppIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val openAppPendingIntent = PendingIntent.getActivity(
            context,
            reminderId + 1000,
            openAppIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("🛒 Store Reminder")
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(false)
            .setContentIntent(openAppPendingIntent)
            .addAction(
                android.R.drawable.checkbox_on_background,
                "✅ MARK DONE",
                markDonePendingIntent
            )
            .build()

        val manager = context.getSystemService(NotificationManager::class.java)
        manager.notify(reminderId, notification)
    }

    fun cancelNotification(reminderId: Int) {
        val manager = context.getSystemService(NotificationManager::class.java)
        manager.cancel(reminderId)
    }
}