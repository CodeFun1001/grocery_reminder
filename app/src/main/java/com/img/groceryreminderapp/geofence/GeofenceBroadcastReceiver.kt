package com.img.groceryreminderapp.geofence

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.img.groceryreminderapp.data.ReminderDatabase
import com.img.groceryreminderapp.data.ReminderRepository
import com.img.groceryreminderapp.notification.NotificationHelper
import com.google.android.gms.location.GeofencingEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GeofenceBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent) ?: return
        if (geofencingEvent.hasError()) return

        val triggeringGeofences = geofencingEvent.triggeringGeofences ?: return

        val dao = ReminderDatabase.getInstance(context).reminderDao()
        val repository = ReminderRepository(dao)
        val notificationHelper = NotificationHelper(context)

        CoroutineScope(Dispatchers.IO).launch {
            for (geofence in triggeringGeofences) {
                val reminderId = geofence.requestId.toIntOrNull() ?: continue

                val allReminders = dao.getAllReminders()
                repository.markCompleted(reminderId)

                showNotificationForReminder(context, reminderId, notificationHelper, repository)
            }
        }
    }

    private suspend fun showNotificationForReminder(
        context: Context,
        reminderId: Int,
        notificationHelper: NotificationHelper,
        repository: ReminderRepository
    ) {
        val db = ReminderDatabase.getInstance(context)
        val reminder = db.reminderDao().getReminderById(reminderId) ?: return
        notificationHelper.showReminderNotification(reminder.id, reminder.itemName)
    }
}