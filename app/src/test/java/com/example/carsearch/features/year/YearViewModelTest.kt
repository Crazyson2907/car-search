package com.example.carsearch.features.year

import com.example.carsearch.common.BaseTest
import com.example.carsearch.domain.core.model.CarSummary
import com.example.carsearch.domain.core.model.main.Manufacturer
import com.example.carsearch.domain.core.model.main.Model
import com.example.carsearch.domain.core.model.main.Year
import com.example.carsearch.domain.network.usecase.FetchYearsUseCase
import com.example.carsearch.presentation.year.YearViewModel
import com.example.carsearch.presentation.year.state.YearsListUiState
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class YearViewModelTest : BaseTest() {

    @RelaxedMockK
    private lateinit var fetchYearsUseCase: FetchYearsUseCase

    private lateinit var viewModel: YearViewModel

    @Before
    fun setupViewModel() {
        MockKAnnotations.init(this)
        viewModel = YearViewModel(fetchYearsUseCase)
    }

    @Test
    fun `loadYears calls use case and updates UI state on success`() = runTest {
        val carSummary = CarSummary(Manufacturer(1, "Audi"), Model("Q7", "Q7"), Year(2005, "2005"))
        val yearsList = listOf(Year(2005, "2005"), Year(2006, "2006"))
        coEvery { fetchYearsUseCase.invoke(carSummary) } returns Result.success(yearsList)

        viewModel.loadYears(carSummary)

        assertThat(viewModel.uiState.value).isEqualTo(YearsListUiState.ListSuccessfullyFetched(yearsList))
        coVerify { fetchYearsUseCase.invoke(carSummary) }
    }

    @Test
    fun `loadYears updates UI state on failure`() = runTest {
        val carSummary = CarSummary(Manufacturer(1, "Audi"), Model("Q7", "Q7"), Year(2005, "2005"))
        val errorMessage = "Network error"
        coEvery { fetchYearsUseCase.invoke(carSummary) } returns Result.failure(Exception(errorMessage))

        viewModel.loadYears(carSummary)

        assertThat(viewModel.uiState.value).isEqualTo(YearsListUiState.ErrorOcurred(errorMessage))
        coVerify { fetchYearsUseCase.invoke(carSummary) }
    }
}