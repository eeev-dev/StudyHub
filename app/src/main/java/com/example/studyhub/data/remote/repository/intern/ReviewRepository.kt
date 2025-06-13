package com.example.studyhub.data.remote.repository.intern

import com.example.studyhub.data.remote.api.intern.GetReviewRequest
import com.example.studyhub.data.remote.api.intern.ReviewApi
import com.example.studyhub.data.remote.api.intern.ReviewRequest
import com.example.studyhub.data.remote.api.intern.ReviewResponse
import com.example.studyhub.data.remote.models.Review
import javax.inject.Inject

class ReviewRepository @Inject constructor(
    private val reviewApi: ReviewApi
) {
    private var cachedReviews: List<Review>? = null

    suspend fun getReviews(placeId: Int): Result<List<Review>> {
        cachedReviews?.let { return Result.success(it) }
        return try {
            val reviews = reviewApi.getReviews(GetReviewRequest(placeId))
            cachedReviews = reviews
            Result.success(reviews)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun sendReview(rating: Int, text: String, date: String, placeId: Int): Result<ReviewResponse> {
        return try {
            val response = reviewApi.postReview(ReviewRequest(rating, text, date.toString(), placeId))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Ошибка: ${response.code()} — ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun clearCache() {
        cachedReviews = null
    }
}