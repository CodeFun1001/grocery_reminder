package com.img.groceryreminderapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: ReminderEntity): Long

    @Query("SELECT * FROM reminders ORDER BY id DESC")
    fun getAllReminders(): Flow<List<ReminderEntity>>

    @Query("SELECT * FROM reminders WHERE status = 'COMPLETED' AND synced = 0")
    suspend fun getCompletedUnsynced(): List<ReminderEntity>

    @Query("UPDATE reminders SET status = 'COMPLETED' WHERE id = :id")
    suspend fun markCompleted(id: Int)

    @Query("UPDATE reminders SET synced = 1 WHERE id = :id")
    suspend fun markSynced(id: Int)

    @Query("SELECT * FROM reminders WHERE id = :id LIMIT 1")
    suspend fun getReminderById(id: Int): ReminderEntity?
}