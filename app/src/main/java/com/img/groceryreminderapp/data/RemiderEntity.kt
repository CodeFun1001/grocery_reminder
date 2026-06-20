package com.img.groceryreminderapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class ReminderStatus { PENDING, COMPLETED }

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val itemName: String,
    val latitude: Double,
    val longitude: Double,
    val status: ReminderStatus = ReminderStatus.PENDING,
    val synced: Boolean = false
)