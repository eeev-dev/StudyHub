package com.example.studyhub.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.studyhub.data.local.dao.PlanDao
import com.example.studyhub.data.local.dao.ReminderDao
import com.example.studyhub.data.local.dao.ScheduleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "studyhub_db"
        )
        .build()
    }

    @Provides
    fun provideScheduleDao(db: AppDatabase): ScheduleDao = db.scheduleDao()

    @Provides
    fun providePlanDao(db: AppDatabase): PlanDao = db.planDao()

    @Provides
    fun provideReminderDao(db: AppDatabase): ReminderDao = db.reminderDao()
}