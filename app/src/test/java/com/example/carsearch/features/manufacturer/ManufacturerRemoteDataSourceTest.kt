package com.example.carsearch.features.manufacturer

import android.util.Log
import com.example.carsearch.common.RemoteDataSourceTest
import com.example.carsearch.common.TestData
import com.example.carsearch.data.remote.RemoteDataSource
import com.example.carsearch.data.remote.features.ManufacturersRemoteDataSource
import com.example.carsearch.data.repository.paging.PagingManager
import com.example.carsearch.domain.core.model.dto.ManufacturerDto
import com.example.carsearch.domain.network.core.features.ManufacturersApiService
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class ManufacturersRemoteDataSourceTest : RemoteDataSourceTest<ManufacturerDto>() {

    @RelaxedMockK
    private lateinit var pagingManager: PagingManager

    @Before
    override fun setup() {
        super.setup()
        pagingManager = mockk(relaxed = true)
        MockKAnnotations.init(this)
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
    }

    @Test
    fun executePageManager() = runTest {
        val remoteDataSource = withData()

        remoteDataSource.doFetching()

        coVerify { pagingManager.nextPage() }
        coVerify { pagingManager.setTotalPages(any()) }
        coVerify { pagingManager.pageSize }
        coVerify { pagingManager.updateNextPage() }
    }

    override fun withNoData(): RemoteDataSource<ManufacturerDto> {
        val api = object : ManufacturersApiService {
            override suspend fun getManufacturers(
                page: Int,
                pageSize: Int
            ): Response<ResponseBody> {
                val contentType = "application/json; charset=utf-8".toMediaType()
                return Response.success(
                    TestData.getResponseJson("{}").toResponseBody(contentType)
                )
            }
        }
        return ManufacturersRemoteDataSource(api, pagingManager)
    }

    override fun withData(): RemoteDataSource<ManufacturerDto> {
        val api = object : ManufacturersApiService {
            override suspend fun getManufacturers(
                page: Int,
                pageSize: Int
            ): Response<ResponseBody> {
                val contentType = "application/json; charset=utf-8".toMediaType()
                return Response.success(
                    TestData.getManufacturerResponseJson().toResponseBody(contentType)
                )
            }
        }
        return ManufacturersRemoteDataSource(api, pagingManager)
    }

    override fun withException(e: Exception): RemoteDataSource<ManufacturerDto> {
        val api = object : ManufacturersApiService {
            override suspend fun getManufacturers(
                page: Int,
                pageSize: Int
            ): Response<ResponseBody> {
                throw e
            }
        }
        return ManufacturersRemoteDataSource(api, pagingManager)
    }
}