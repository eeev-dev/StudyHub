package com.example.studyhub.data.remote.repository.intern

import com.example.studyhub.data.remote.api.intern.CompanyApi
import com.example.studyhub.data.remote.models.Place
import javax.inject.Inject

class CompaniesRepository @Inject constructor(
    private val companyApi: CompanyApi
) {
    private var cachedCompanies: List<Place>? = null

    suspend fun getCompanies(): Result<List<Place>> {
        cachedCompanies?.let { return Result.success(it) }
        return try {
            val places = companyApi.getPlaces()
            cachedCompanies = places
            Result.success(places)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun clearCache() {
        cachedCompanies = null
    }
}