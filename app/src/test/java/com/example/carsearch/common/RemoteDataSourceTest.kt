package com.example.carsearch.common

import com.example.carsearch.data.remote.RemoteDataSource
import com.example.carsearch.domain.core.model.CarSummary
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.IOException

abstract class RemoteDataSourceTest<T> : BaseTest() {

    /**
     * Provides an instance of `RemoteDataSource` with no data available.
     */
    abstract fun withNoData(): RemoteDataSource<T>

    /**
     * Provides an instance of `RemoteDataSource` with pre-populated data.
     */
    abstract fun withData(): RemoteDataSource<T>

    /**
     * Provides an instance of `RemoteDataSource` that will throw an exception when fetching data.
     */
    abstract fun withException(e: Exception): RemoteDataSource<T>

    @Test
    fun `returns error when no data is available`() = runTest {
        val expectedError = "No record found"
        val dataSource = withNoData()
        val result = dataSource.doFetching(CarSummary())
        val isCorrectError = isFailureWithMessage(result, expectedError)

        assertThat(isCorrectError).isTrue()
    }

    @Test
    fun `returns non-empty list when data is available`() = runTest {
        val dataSource = withData()
        val result = dataSource.doFetching(CarSummary())

        val actualSize = result.getOrThrow().size
        assertThat(actualSize).isGreaterThan(0)
    }

    @Test
    fun `returns specific error on network failure`() = runTest {
        val expectedError = "Please check your internet connection"
        val dataSource = withException(IOException())

        val result = dataSource.doFetching(CarSummary())
        val isCorrectError = isFailureWithMessage(result, expectedError)

        assertThat(isCorrectError).isTrue()
    }
}