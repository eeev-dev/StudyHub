package com.example.studyhub.data.remote.api.intern

import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface LetterApi {
    @Multipart
    @POST("/intern/letter")
    suspend fun sendLetter(
        @Part image: MultipartBody.Part,
        @Part("id") internId: Int,
        @Part("title") title: String
    ): Result<InternResponse>
}