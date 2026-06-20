package com.img.groceryreminderapp.network

data class SyncRequest(
    val id: Int,
    val itemName: String,
    val status: String
)

data class SyncResponse(
    val success: Boolean
)