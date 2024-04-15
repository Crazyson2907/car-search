package com.example.carsearch.data.remote

import com.example.carsearch.domain.core.model.CarSummary
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException

abstract class RemoteDataSource<out T> {

    abstract suspend fun onFetching(carSummary: CarSummary?): Response<ResponseBody>
    abstract fun getDto(key: String, value: String): T

    suspend fun doFetching(carSummary: CarSummary? = null): Result<List<T>> {
        return try {
            val apiResponse = onFetching(carSummary)
            if (!apiResponse.isSuccessful) {
                return Result.failure(Throwable(apiResponse.message() ?: "Unknown Error"))
            }

            val body = apiResponse.body()?.string()
            if (body.isNullOrEmpty()) {
                return Result.failure(Throwable("No content returned from the server"))
            }

            val result = deserializeJson(body)
            if (result.isEmpty()) {
                Result.failure(Throwable("No record found"))
            } else {
                Result.success(result)
            }
        } catch (e: IOException) {
            Result.failure(Throwable("Please check your internet connection: ${e.message}"))
        } catch (e: IllegalStateException) {
            Result.failure(Throwable("Invalid state encountered: ${e.message}"))
        } catch (e: JSONException) {
            Result.failure(Throwable("Failed to parse response: ${e.message}"))
        }
    }

    protected open fun deserializeJson(jsonString: String): List<T> {
        val jsonObject = JSONObject(jsonString)
        val manufacturersJsonObj = jsonObject.getJSONObject("wkda")
        return manufacturersJsonObj.keys().asSequence().map { key ->
            getDto(key, manufacturersJsonObj.getString(key))
        }.toList()
    }
}