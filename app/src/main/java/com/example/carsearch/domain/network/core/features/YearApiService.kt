package com.example.carsearch.domain.network.core.features

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YearApiService {

    @GET("car-types/built-dates")
    suspend fun getBuildDates(
        @Query("manufacturer") manufacturer: Int,
        @Query("main-type") mainType: String
    ): Response<ResponseBody>
}