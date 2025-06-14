package com.example.studyhub.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.studyhub.data.local.entities.PlanEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlanDao {
    @Query("SELECT * FROM plans1")
    fun getPlans(): Flow<List<PlanEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(plan: PlanEntity)

    @Delete
    suspend fun delete(plan: PlanEntity)
}