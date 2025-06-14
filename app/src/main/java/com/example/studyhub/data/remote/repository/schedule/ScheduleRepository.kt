package com.example.studyhub.data.remote.repository.schedule

import com.example.studyhub.data.remote.api.intern.CompanyApi
import com.example.studyhub.data.remote.api.schedule.ScheduleApi
import com.example.studyhub.data.remote.models.Place
import com.example.studyhub.data.remote.models.Schedule
import javax.inject.Inject

class ScheduleRepository @Inject constructor(
    private val api: ScheduleApi
) {
    suspend fun getSchedule(studentId: String): Result<List<Schedule>> {
        return try {
            val schedule = api.getSchedule(ScheduleApi.ScheduleRequest(studentId))
            Result.success(schedule)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}