package com.example.carsearch.features.model

import com.example.carsearch.common.BaseTest
import com.example.carsearch.data.remote.features.ModelsDataSource
import com.example.carsearch.domain.core.model.CarSummary
import com.example.carsearch.domain.core.model.main.Manufacturer
import com.example.carsearch.domain.network.core.features.ModelsApiService
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response
import java.io.IOException

@RunWith(JUnit4::class)
class ModelRemoteDataSourceTest : BaseTest() {

    @RelaxedMockK
    private lateinit var modelsApiService: ModelsApiService
    private lateinit var modelsRemoteDataSource: ModelsDataSource

    @Before
    override fun setup() {
        super.setup()
        modelsRemoteDataSource = ModelsDataSource(modelsApiService)
    }

    @Test
    fun `fetch models from API service`() = runTest {
        val carSummary = CarSummary(Manufacturer(id = 1, name = "Audi"))
        val response = mockk<Response<ResponseBody>>()

        coEvery { modelsApiService.getCarTypes(1) } returns response
        coEvery { response.isSuccessful } returns true
        coEvery { response.body() } returns ResponseBody.create(null, """{"modelId":"Q7","modelName":"Q7"}""")

        modelsRemoteDataSource.onFetching(carSummary)

        coVerify(exactly = 1) { modelsApiService.getCarTypes(1) }
    }

    @Test
    fun `handle API service errors`() = runTest {
        val carSummary = CarSummary(Manufacturer(id = 1, name = "Audi"))
        val exception = IOException("Network error")

        coEvery { modelsApiService.getCarTypes(130) } throws exception

        val result = modelsRemoteDataSource.onFetching(carSummary)

        assertThat(result).isInstanceOf(Response::class.java)
        coVerify(exactly = 1) { modelsApiService.getCarTypes(1) }
    }
}