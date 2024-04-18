package com.example.carsearch.features.manufacturer

import com.example.carsearch.common.BaseTest
import com.example.carsearch.common.TestData
import com.example.carsearch.data.remote.RemoteDataSource
import com.example.carsearch.data.repository.feature.ManufacturersRepositoryImpl
import com.example.carsearch.domain.core.mapper.ManufacturerMapper
import com.example.carsearch.domain.core.model.dto.ManufacturerDto
import com.example.carsearch.domain.core.model.main.Manufacturer
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ManufacturersRepositoryTest : BaseTest() {

    @RelaxedMockK
    private lateinit var remoteDataSource: RemoteDataSource<ManufacturerDto>

    @RelaxedMockK
    private lateinit var manufacturerMapper: ManufacturerMapper<List<ManufacturerDto>, List<Manufacturer>>

    private lateinit var manufacturersRepo: ManufacturersRepositoryImpl

    @Before
    override fun setup() {
        super.setup()
        manufacturersRepo = ManufacturersRepositoryImpl(remoteDataSource, manufacturerMapper)
    }

    @Test
    fun fetchManufacturersShouldReturnErrorWhenRemoteSourceFails() = runTest {
        val errorMessage = "Network error"
        coEvery { remoteDataSource.doFetching() } returns Result.failure(Exception(errorMessage))

        val result = manufacturersRepo.fetchManufacturers()

        val actual = result.fold(
            onSuccess = { false },
            onFailure = { it.message == errorMessage }
        )
        assertTrue(actual)
        coVerify(exactly = 1) { remoteDataSource.doFetching() }
    }

    @Test
    fun fetchManufacturersShouldReturnMappedDomainModels() = runTest {
        val manufacturersDto = TestData.getManufacturersAsDto()
        val manufacturerDomain = TestData.getManufacturersAsDomainModels()
        coEvery { remoteDataSource.doFetching() } returns Result.success(manufacturersDto)
        coEvery { manufacturerMapper.map(manufacturersDto) } returns manufacturerDomain

        val result = manufacturersRepo.fetchManufacturers()

        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull(), manufacturerDomain)
        coVerify(exactly = 1) { manufacturerMapper.map(manufacturersDto) }
    }
}