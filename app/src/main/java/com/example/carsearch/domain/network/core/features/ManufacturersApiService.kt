package com.example.carsearch.domain.network.core.features

import com.example.carsearch.domain.network.core.CarsApiService
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ManufacturersApiService : CarsApiService {

    @GET("car-types/manufacturer")
    suspend fun getManufacturers(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Response<ResponseBody>
}