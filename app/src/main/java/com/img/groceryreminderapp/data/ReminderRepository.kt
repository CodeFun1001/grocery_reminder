package com.img.groceryreminderapp.data

import kotlinx.coroutines.flow.Flow

class ReminderRepository(private val dao: ReminderDao) {

    fun getAllReminders(): Flow<List<ReminderEntity>> = dao.getAllReminders()

    suspend fun insertReminder(reminder: ReminderEntity): Long = dao.insertReminder(reminder)

    suspend fun markCompleted(id: Int) = dao.markCompleted(id)

    suspend fun markSynced(id: Int) = dao.markSynced(id)

    suspend fun getCompletedUnsynced(): List<ReminderEntity> = dao.getCompletedUnsynced()
}