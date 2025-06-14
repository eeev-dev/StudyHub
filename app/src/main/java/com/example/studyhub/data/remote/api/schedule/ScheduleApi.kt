package com.example.studyhub.data.remote.api.schedule

import com.example.studyhub.data.remote.api.login.LoginApi.LoginRequest
import com.example.studyhub.data.remote.models.Schedule
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ScheduleApi {
    data class ScheduleRequest(val student_id: String)

    @POST("/api/schedule")
    suspend fun getSchedule(
        @Body request: ScheduleRequest
    ): List<Schedule>
}