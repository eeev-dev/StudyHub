package com.example.studyhub.data.remote.repository.intern

import com.example.studyhub.data.remote.api.intern.InternApi
import com.example.studyhub.data.remote.api.intern.InternPlaceRequest
import com.example.studyhub.data.remote.api.intern.InternRequest
import com.example.studyhub.data.remote.api.intern.InternResponse
import javax.inject.Inject

class InternRepository @Inject constructor(
    private val api: InternApi
) {

    suspend fun sendIntern(internId: Int, placeId: Int): Result<InternResponse> {
        return try {
            val response = api.postIntern(InternPlaceRequest(internId, placeId))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Ошибка: ${response.code()} — ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getIntern(internId: Int): Result<InternResponse> {
        return try {
            val response = api.getIntern(InternRequest(internId))
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Result.success(body)
                } else {
                    Result.failure(Exception(body?.message ?: "Ошибка данных"))
                }
            } else {
                Result.failure(Exception("Ошибка сервера: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}