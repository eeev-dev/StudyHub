package com.example.studyhub.data.remote.repository.login

import com.example.studyhub.data.remote.api.login.PingResponse
import com.example.studyhub.data.remote.api.login.PingApi

class PingRepository(private val api: PingApi) {
    suspend fun ping(studentId: String): Result<PingResponse> {
        return try {
            val response = api.ping(PingApi.PingRequest(studentId))
            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                if (body.success) {
                    Result.success(body)  // Возвращаем весь объект LoginResponse
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
}