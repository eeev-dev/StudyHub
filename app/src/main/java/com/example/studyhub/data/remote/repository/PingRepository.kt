package com.example.studyhub.data.remote.repository

import com.example.studyhub.data.remote.api.LoginApi
import com.example.studyhub.data.remote.api.LoginApi.LoginRequest
import com.example.studyhub.data.remote.api.LoginResponse
import com.example.studyhub.data.remote.api.PingResponse
import com.example.studyhub.data.remote.api.PingApi

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