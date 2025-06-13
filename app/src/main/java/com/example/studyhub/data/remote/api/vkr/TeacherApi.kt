package com.example.studyhub.data.remote.api.vkr

import retrofit2.http.GET

interface TeacherApi {
    @GET("/api/teachers")
    suspend fun getTeachers(): List<String>
}