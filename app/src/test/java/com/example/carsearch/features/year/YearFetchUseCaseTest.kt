package com.example.carsearch.features.year

import com.example.carsearch.common.BaseTest
import com.example.carsearch.data.repository.feature.YearsRepositoryImpl
import com.example.carsearch.domain.core.model.CarSummary
import com.example.carsearch.domain.core.model.main.Manufacturer
import com.example.carsearch.domain.core.model.main.Model
import com.example.carsearch.domain.network.usecase.FetchYearsUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FetchYearsUseCaseShould : BaseTest() {
    private lateinit var fetchYearsUseCase: FetchYearsUseCase
    @RelaxedMockK
    private lateinit var yearsRepository: YearsRepositoryImpl

    @Before
    override fun setup() {
        super.setup()
        fetchYearsUseCase = FetchYearsUseCase(yearsRepository)
    }

    @Test
    fun `invoke calls repository to fetch years`() = runTest {
        val carSummary = CarSummary(Manufacturer(1, "Audi"), Model("Q7", "Q7"))
        coEvery { yearsRepository.fetchYears(carSummary) } returns Result.success(emptyList())

        fetchYearsUseCase(carSummary)

        coVerify { yearsRepository.fetchYears(carSummary) }
    }
}