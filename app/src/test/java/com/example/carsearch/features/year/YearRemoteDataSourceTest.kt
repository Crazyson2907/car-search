package com.example.carsearch.features.year

import com.example.carsearch.common.BaseTest
import com.example.carsearch.data.remote.features.YearDataSource
import com.example.carsearch.domain.core.model.CarSummary
import com.example.carsearch.domain.core.model.main.Manufacturer
import com.example.carsearch.domain.core.model.main.Model
import com.example.carsearch.domain.network.core.features.YearApiService
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
class YearRemoteDataSourceTest : BaseTest() {

    @RelaxedMockK
    private lateinit var yearApiService: YearApiService
    private lateinit var yearRemoteDataSource: YearDataSource

    @Before
    override fun setup() {
        super.setup()
        yearRemoteDataSource = YearDataSource(yearApiService)
    }

    @Test
    fun `fetch years from API service`() = runTest {
        val carSummary = CarSummary(Manufacturer(id = 1, name = "Audi"), Model(id = "Q7", name = "Q7"))
        val response = mockk<Response<ResponseBody>>()

        coEvery { yearApiService.getBuildDates(1,  "Q7") } returns response
        coEvery { response.isSuccessful } returns true
        coEvery { response.body() } returns ResponseBody.create(null, """{"wkda":{"2005":"2005","2006":"2006"}}""")

        yearRemoteDataSource.onFetching(carSummary)

        coVerify(exactly = 1) { yearApiService.getBuildDates(1, "Q7") }
    }

    @Test
    fun `handle API service errors`() = runTest {
        val carSummary = CarSummary(Manufacturer(id = 1, name = "Audi"), Model(id = "Q7", name = "Q7"))
        val exception = IOException("Network error")

        coEvery { yearApiService.getBuildDates(1, "Q7") } throws exception

        val result = yearRemoteDataSource.onFetching(carSummary)

        assertThat(result).isInstanceOf(Response::class.java)
        coVerify(exactly = 1) { yearApiService.getBuildDates(1, "Q7") }
    }
}