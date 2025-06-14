package com.example.studyhub.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedule")
data class ScheduleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String?,
    val type: String?,
    val teacher: String?,
    val group: String?,
    val term: String?,
    val day: String?,
    val time: String?,
    val parity: String?,
    val is_online: Int?,
    val link: String?,
    val room: String?
)
