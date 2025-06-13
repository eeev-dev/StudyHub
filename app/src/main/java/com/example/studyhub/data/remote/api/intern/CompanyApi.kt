package com.example.studyhub.data.remote.api.intern

import com.example.studyhub.data.remote.models.Place
import retrofit2.http.GET

interface CompanyApi {
    @GET("/api/places")
    suspend fun getPlaces(): List<Place>
}