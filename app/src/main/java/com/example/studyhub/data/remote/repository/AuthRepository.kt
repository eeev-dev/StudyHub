package com.example.studyhub.data.remote.repository

import com.example.studyhub.data.remote.api.LoginApi
import com.example.studyhub.data.remote.api.LoginApi.LoginRequest
import com.example.studyhub.data.remote.api.LoginResponse

class AuthRepository(private val api: LoginApi) {

    suspend fun login(studentId: String): Result<LoginResponse> {
        return try {
            val response = api.login(LoginRequest(studentId))
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
}
