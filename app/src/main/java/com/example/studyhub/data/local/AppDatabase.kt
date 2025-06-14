package com.example.studyhub.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.studyhub.data.local.dao.PlanDao
import com.example.studyhub.data.local.dao.ReminderDao
import com.example.studyhub.data.local.dao.ScheduleDao
import com.example.studyhub.data.local.entities.PlanEntity
import com.example.studyhub.data.local.entities.ReminderEntity
import com.example.studyhub.data.local.entities.ScheduleEntity

@Database(
    entities = [ScheduleEntity::class, PlanEntity::class, ReminderEntity::class],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun scheduleDao(): ScheduleDao
    abstract fun planDao(): PlanDao
    abstract fun reminderDao(): ReminderDao
}