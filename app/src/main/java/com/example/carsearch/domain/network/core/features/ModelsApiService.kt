package com.example.carsearch.domain.network.core.features

import com.example.carsearch.domain.network.core.CarsApiService
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ModelsApiService : CarsApiService {

    @GET("car-types/main-types")
    suspend fun getCarTypes(
        @Query("manufacturer") manufacturerId: Int
    ): Response<ResponseBody>
}