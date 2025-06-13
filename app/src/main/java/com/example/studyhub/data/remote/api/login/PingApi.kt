package com.example.studyhub.data.remote.api.login

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PingApi {
    data class PingRequest(val student_id: String)

    @POST("/ping")
    suspend fun ping(
        @Body request: PingRequest
    ): Response<PingResponse>
}

data class PingResponse(
    val success: Boolean,
    val message: String
)