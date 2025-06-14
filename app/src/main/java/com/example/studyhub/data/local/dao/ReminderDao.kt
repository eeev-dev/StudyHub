package com.example.studyhub.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.studyhub.data.local.entities.ReminderEntity
import com.example.studyhub.data.local.entities.ScheduleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    @Query("SELECT * FROM reminders_new")
    fun getAll(): Flow<List<ReminderEntity>>

    @Query("DELETE FROM reminders_new")
    suspend fun clearAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reminder: ReminderEntity)

    @Delete
    suspend fun delete(reminder: ReminderEntity)
}