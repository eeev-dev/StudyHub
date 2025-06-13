package com.example.studyhub.data.remote.repository.vkr

import com.example.studyhub.data.remote.api.vkr.GraduateApi
import com.example.studyhub.data.remote.api.vkr.GraduateApi.GraduateRequest
import com.example.studyhub.data.remote.api.vkr.GraduateApi.SupervisorRequest
import com.example.studyhub.data.remote.api.vkr.GraduateApi.TopicRequest
import com.example.studyhub.data.remote.api.vkr.GraduateResponse
import com.example.studyhub.data.remote.api.vkr.JustResponse

class GraduateRepository(private val api: GraduateApi) {
    suspend fun get(studentId: String): Result<GraduateResponse> {
        return try {
            val response = api.get(GraduateRequest(studentId))
            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                if (body.success) {
                    Result.success(body)
                } else {
                    Result.failure(Exception(body.message))
                }
            } else {
                Result.failure(Exception("Ошибка сервера: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun postSupervisor(studentId: String, supervisorName: String): Result<JustResponse> {
        return try {
            val response = api.postSupervisor(SupervisorRequest(studentId, supervisorName))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Ошибка: ${response.code()} — ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun postTopic(studentId: String, topic: String): Result<JustResponse> {
        return try {
            val response = api.postTopic(TopicRequest(studentId, topic))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Ошибка: ${response.code()} — ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}