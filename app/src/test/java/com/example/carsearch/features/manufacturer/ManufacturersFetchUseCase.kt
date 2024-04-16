package com.example.carsearch.features.manufacturer

import com.example.carsearch.common.BaseTest
import com.example.carsearch.data.repository.feature.ManufacturersRepositoryImpl
import com.example.carsearch.domain.core.usecase.FetchManufacturersUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ManufacturersFetchUseCase : BaseTest() {

    @RelaxedMockK
    private lateinit var manufacturersRepo: ManufacturersRepositoryImpl
    private lateinit var fetchManufacturersUseCase: FetchManufacturersUseCase

    @Before
    override fun setup() {
        super.setup()
        fetchManufacturersUseCase = FetchManufacturersUseCase(manufacturersRepo)
    }

    @Test
    fun `ensure fetchManufacturers is called`() = runTest {
        coEvery { manufacturersRepo.fetchManufacturers() } returns Result.success(emptyList())
        fetchManufacturersUseCase()
        coVerify(exactly = 1) { manufacturersRepo.fetchManufacturers() }
    }
}