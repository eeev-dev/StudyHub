package com.example.studyhub.data.remote.repository.vkr

import com.example.studyhub.data.remote.api.vkr.TeacherApi
import javax.inject.Inject

class TeacherRepository @Inject constructor(
    private val api: TeacherApi
) {
    private var cachedTeachers: List<String>? = null

    suspend fun getTeachers(): Result<List<String>> {
        cachedTeachers?.let { return Result.success(it) }
        return try {
            val teachers = api.getTeachers()
            cachedTeachers = teachers
            Result.success(teachers)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun clearCache() {
        cachedTeachers = null
    }
}