package com.example.studyhub.data.remote

import com.example.studyhub.data.remote.models.PostRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/api/posts")
    fun createPost(@Body post: PostRequest): Call<Void>
}
