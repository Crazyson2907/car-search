package com.example.carsearch.di

import com.example.carsearch.data.remote.Networking
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideInterceptor(): Interceptor {
        return Networking().provideInterceptor()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        return Networking().provideOkHttpClient(interceptor)
    }

    @Singleton
    @Provides
    fun provideBaseUrl(): String {
        return Networking().provideBaseUrl()
    }

    @Singleton
    @Provides
    fun provideRetrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit {
        return Networking().provideRetrofit(baseUrl, okHttpClient)
    }
}