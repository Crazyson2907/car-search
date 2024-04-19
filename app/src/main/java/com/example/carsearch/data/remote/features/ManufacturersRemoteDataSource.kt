package com.example.carsearch.data.remote.features

import android.util.Log
import com.example.carsearch.data.remote.RemoteDataSource
import com.example.carsearch.data.repository.paging.PagingManager
import com.example.carsearch.domain.core.model.CarSummary
import com.example.carsearch.domain.core.model.dto.ManufacturerDto
import com.example.carsearch.domain.network.core.features.ManufacturersApiService
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException

class ManufacturersRemoteDataSource(
    private val remoteApiImp: ManufacturersApiService,
    private val pagingManager: PagingManager
) : RemoteDataSource<ManufacturerDto>() {

    override suspend fun onFetching(carSummary: CarSummary?): Response<ResponseBody> {
        val nextPage = pagingManager.nextPage()
        val pageSize = pagingManager.pageSize
        Log.d("Paging", "Fetching page: $nextPage with size: $pageSize")

        return try {
            remoteApiImp.getManufacturers(nextPage, pageSize)
        } catch (e: IOException) {
            Response.error(408, "Network error occurred".toResponseBody(null))
        }
    }

    override fun getDto(key: String, value: String): ManufacturerDto {
        return ManufacturerDto(key, value)
    }

    override fun deserializeJson(jsonString: String): List<ManufacturerDto> {
        val jsonObject = JSONObject(jsonString)
        val pageCount = jsonObject.getInt("totalPageCount")
        pagingManager.setTotalPages(pageCount)
        pagingManager.updateNextPage()
        Log.d("Paging", "Total pages set to: $pageCount")

        return super.deserializeJson(jsonString)
    }
}