package com.example.studyhub.data.remote

import com.example.studyhub.data.remote.api.intern.CompanyApi
import com.example.studyhub.data.remote.api.intern.InternApi
import com.example.studyhub.data.remote.api.intern.LetterApi
import com.example.studyhub.data.remote.api.intern.ReviewApi
import com.example.studyhub.data.remote.api.login.LoginApi
import com.example.studyhub.data.remote.api.login.PingApi
import com.example.studyhub.data.remote.api.schedule.ScheduleApi
import com.example.studyhub.data.remote.api.vkr.GraduateApi
import com.example.studyhub.data.remote.api.vkr.TeacherApi
import com.example.studyhub.data.remote.models.Review
import com.example.studyhub.data.remote.repository.intern.CompaniesRepository
import com.example.studyhub.data.remote.repository.intern.InternRepository
import com.example.studyhub.data.remote.repository.intern.ReviewRepository
import com.example.studyhub.data.remote.repository.login.AuthRepository
import com.example.studyhub.data.remote.repository.login.PingRepository
import com.example.studyhub.data.remote.repository.schedule.ScheduleRepository
import com.example.studyhub.data.remote.repository.vkr.GraduateRepository
import com.example.studyhub.data.remote.repository.vkr.TeacherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl() = "https://deskhub.onrender.com"

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providePingApi(retrofit: Retrofit): PingApi {
        return retrofit.create(PingApi::class.java)
    }

    @Provides
    @Singleton
    fun providePingRepository(api: PingApi): PingRepository {
        return PingRepository(api)
    }

    @Provides
    @Singleton
    fun provideLoginApi(retrofit: Retrofit): LoginApi {
        return retrofit.create(LoginApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(api: LoginApi): AuthRepository {
        return AuthRepository(api)
    }

    @Provides
    @Singleton
    fun provideGraduateApi(retrofit: Retrofit): GraduateApi {
        return retrofit.create(GraduateApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGraduateRepository(api: GraduateApi): GraduateRepository {
        return GraduateRepository(api)
    }

    @Provides
    @Singleton
    fun provideTeacherApi(retrofit: Retrofit): TeacherApi {
        return retrofit.create(TeacherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTeacherRepository(api: TeacherApi): TeacherRepository {
        return TeacherRepository(api)
    }

    @Provides
    fun provideCompanyApi(retrofit: Retrofit): CompanyApi {
        return retrofit.create(CompanyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCompaniesRepository(api: CompanyApi): CompaniesRepository {
        return CompaniesRepository(api)
    }

    @Provides
    @Singleton
    fun provideInternApi(retrofit: Retrofit): InternApi {
        return retrofit.create(InternApi::class.java)
    }

    @Provides
    @Singleton
    fun provideInternRepository(api: InternApi): InternRepository {
        return InternRepository(api)
    }

    @Provides
    @Singleton
    fun provideLetterApi(retrofit: Retrofit): LetterApi {
        return retrofit.create(LetterApi::class.java)
    }

    @Provides
    @Singleton
    fun provideReviewApi(retrofit: Retrofit): ReviewApi {
        return retrofit.create(ReviewApi::class.java)
    }

    @Provides
    @Singleton
    fun provideReviewRepository(api: ReviewApi): ReviewRepository {
        return ReviewRepository(api)
    }

    @Provides
    @Singleton
    fun provideScheduleApi(retrofit: Retrofit): ScheduleApi {
        return retrofit.create(ScheduleApi::class.java)
    }

    @Provides
    @Singleton
    fun provideScheduleRepository(api: ScheduleApi): ScheduleRepository {
        return ScheduleRepository(api)
    }
}
