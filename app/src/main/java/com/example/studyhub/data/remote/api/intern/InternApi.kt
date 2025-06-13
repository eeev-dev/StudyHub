package com.example.studyhub.data.remote.api.intern

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface InternApi {
    @POST("/intern/post")
    suspend fun postIntern(@Body request: InternPlaceRequest): Response<InternResponse>

    @POST("/intern/get")
    suspend fun getIntern(
        @Body request: InternRequest
    ): Response<InternResponse>
}

data class InternRequest(val student_id: Int)

data class InternPlaceRequest(
    val student_id: Int,
    val place_id: Int
)

data class InternResponse(
    val success: Boolean = false,
    val status: String = "",
    val deadline: String = "",
    val place: String = "",
    val place_id: Int = -1,
    val head_teacher: String = "",
    val message: String? = null
)