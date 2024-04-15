package com.example.carsearch.domain.network.core

import com.example.carsearch.domain.network.model.BuildDateResponse
import com.example.carsearch.domain.network.model.ManufacturerResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface CarsApiService {

    @Headers("Content-Type: application/json")
    @GET("car-types/manufacturer")
    suspend fun getManufacturers(
        @Query("page") pag: Int,
        @Query("pageSize") pageSize: Int
    ): Response<ResponseBody>

    @Headers("Content-Type: application/json")
    @GET("car-types/main-types")
    suspend fun getCarTypes(@Query("manufacturer") manufacturerId: Int): Response<ResponseBody>

    @Headers("Content-Type: application/json")
    @GET("car-types/built-dates")
    suspend fun getBuildDates(
        @Query("manufacturer") manufacturer: Int,
        @Query("main-type") mainType: String
    ): Response<ResponseBody>
}