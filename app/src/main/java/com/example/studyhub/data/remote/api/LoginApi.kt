package com.example.studyhub.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    data class LoginRequest(val student_number: String)

    @POST("/student/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>
}

data class LoginResponse(
    val success: Boolean,
    val id: Int?,
    val message: String
)
