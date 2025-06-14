package com.example.studyhub.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plans1")
data class PlanEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String,
    val deadline: String?,
    val isFinished: Boolean = false
)
