package com.example.studyhub.data.remote

import android.content.Context
import com.example.studyhub.data.remote.api.LoginApi
import com.example.studyhub.data.remote.api.PingApi
import com.example.studyhub.data.remote.api.vkr.GraduateApi
import com.example.studyhub.data.remote.api.vkr.TeacherApi
import com.example.studyhub.data.remote.repository.AuthRepository
import com.example.studyhub.data.remote.repository.PingRepository
import com.example.studyhub.data.remote.repository.vkr.GraduateRepository
import com.example.studyhub.data.remote.repository.vkr.TeacherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
}
