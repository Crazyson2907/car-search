package com.example.carsearch.data.remote

import android.util.Log
import com.example.carsearch.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class Networking {

    fun provideRetrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()
    }

    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient().newBuilder()
            .addInterceptor(logging)
            .addInterceptor(interceptor)
            .build()
    }

    fun provideBaseUrl(): String {
        return BuildConfig.BASE_URL
    }

    fun provideInterceptor(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val urlWithApiKey = original.url.newBuilder()
                .addQueryParameter("wa_key", BuildConfig.API_KEY)
                .build()

            val newRequest = original.newBuilder().url(urlWithApiKey).build()
            Log.d("HttpInterceptor", "Requesting URL: ${newRequest.url}")
            chain.proceed(newRequest)
        }
    }
}