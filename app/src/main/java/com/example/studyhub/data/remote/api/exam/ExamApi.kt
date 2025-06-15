package com.example.studyhub.data.remote.api.exam

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Streaming

interface ExamApi {
    data class ExamRequest(val student_id: String)

    @POST("/schedule/exam")
    @Streaming
    suspend fun getExams(
        @Body request: ExamRequest
    ): Response<ResponseBody>
}