package com.example.studyhub.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminders_new")
data class ReminderEntity(
    @PrimaryKey val id: String,
    val text: String,
    val url: String
)