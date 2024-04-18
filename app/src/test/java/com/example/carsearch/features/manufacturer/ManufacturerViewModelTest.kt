package com.example.carsearch.features.manufacturer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.carsearch.common.BaseTest
import com.example.carsearch.common.TestData
import com.example.carsearch.domain.core.usecase.FetchManufacturersUseCase
import com.example.carsearch.presentation.manufacturers.ManufacturerViewModel
import com.example.carsearch.presentation.manufacturers.state.ManufacturersListUiState
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ManufacturerViewModelTest : BaseTest() {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var fetchManufactureUseCase: FetchManufacturersUseCase

    private lateinit var viewModel: ManufacturerViewModel

    @Before
    override fun setup() {
        super.setup()
        MockKAnnotations.init(this)
        viewModel = ManufacturerViewModel(fetchManufactureUseCase)
    }

    @Test
    fun fetchManufacturers() = runTest {
        val manufacturers = TestData.getManufacturersAsDomainModels()
        coEvery { fetchManufactureUseCase() } returns Result.success(manufacturers)
        viewModel.loadManufacturers()
        coVerify(exactly = 1) { fetchManufactureUseCase() }
        val expectedStates = listOf(
            ManufacturersListUiState.Loading,
            ManufacturersListUiState.ListSuccessfullyFetched(manufacturers)
        )
        val actualStates = viewModel.uiState.take(2).toList()
        assertThat(actualStates).isEqualTo(expectedStates)
    }
}