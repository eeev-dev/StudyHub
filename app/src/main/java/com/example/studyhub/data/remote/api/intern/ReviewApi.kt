package com.example.studyhub.data.remote.api.intern

import com.example.studyhub.data.remote.models.Review
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ReviewApi {
    @POST("/api/reviews")
    suspend fun getReviews(@Body request: GetReviewRequest): List<Review>

    @POST("/review/post")
    suspend fun postReview(@Body request: ReviewRequest): Response<ReviewResponse>
}

data class GetReviewRequest(
    val place_id: Int
)

data class ReviewRequest(
    val rating: Int,
    val text: String,
    val date: String,
    val place_id: Int
)

data class ReviewResponse(
    val success: Boolean,
    val message: String
)