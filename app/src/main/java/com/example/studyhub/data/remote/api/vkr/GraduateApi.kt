package com.example.studyhub.data.remote.api.vkr

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GraduateApi {
    data class GraduateRequest(val student_id: String)
    data class SupervisorRequest(val student_id: String, val supervisor_name: String)
    data class TopicRequest(val student_id: String, val topic: String)

    @POST("/vkr/get")
    suspend fun get(
        @Body request: GraduateRequest
    ): Response<GraduateResponse>

    @POST("/vkr/post-supervisor")
    suspend fun postSupervisor(
        @Body request: SupervisorRequest
    ): Response<JustResponse>

    @POST("/vkr/post-topic")
    suspend fun postTopic(
        @Body request: TopicRequest
    ): Response<JustResponse>
}

data class GraduateResponse(
    val supervisor_deadline: String = "",
    val topic_deadline: String = "",
    val supervisor:  String = "",
    val topic: String = "",
    val status: String = "",
    val message: String = "",
    val success: Boolean
)

data class JustResponse(
    val success: Boolean,
    val message: String
)