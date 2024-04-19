package com.example.carsearch.data.remote.features

import android.util.Log
import com.example.carsearch.data.remote.RemoteDataSource
import com.example.carsearch.domain.core.model.CarSummary
import com.example.carsearch.domain.core.model.dto.ModelDto
import com.example.carsearch.domain.network.core.features.ModelsApiService
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class ModelsDataSource(private val remoteApi: ModelsApiService) : RemoteDataSource<ModelDto>() {

    override suspend fun onFetching(carSummary: CarSummary?): Response<ResponseBody> {
        if (carSummary?.manufacturer == null) {
            Log.e("ModelsRemoteDataSource", "Car information or manufacturer ID is null")
            return Response.error(
                400,
                "Invalid request data. Car information is incomplete.".toResponseBody()
            )
        }

        return try {
            remoteApi.getCarTypes(carSummary.manufacturer.id)
        } catch (e: Exception) {
            Log.e("ModelsRemoteDataSource", "Network request failed: ${e.message}")
            Response.error(
                500,
                ("Network error occurred: ${e.message}").toResponseBody()
            )
        }
    }

    override fun getDto(key: String, value: String): ModelDto {
        return ModelDto(key, value)
    }
}