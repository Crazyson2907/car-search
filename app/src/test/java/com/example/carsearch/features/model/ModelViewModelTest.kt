package com.example.carsearch.features.model

import com.example.carsearch.common.BaseTest
import com.example.carsearch.common.TestData
import com.example.carsearch.domain.core.model.CarSummary
import com.example.carsearch.domain.network.usecase.FetchModelsUseCase
import com.example.carsearch.presentation.types.ModelViewModel
import com.example.carsearch.presentation.types.state.ModelsListUiState
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ModelViewModelTest : BaseTest() {
    private lateinit var viewModel: ModelViewModel

    @RelaxedMockK
    private lateinit var fetchModelsUseCase: FetchModelsUseCase

    @Before
    override fun setup() {
        super.setup()
        viewModel = ModelViewModel(fetchModelsUseCase)
    }

    @Test
    fun `loadModels updates ui state on success`() = runTest {
        val carSummary = CarSummary()
        val models = TestData.getModelAsDomainModels()

        coEvery { fetchModelsUseCase(carSummary) } returns Result.success(models)

        viewModel.loadModels(carSummary)

        assert(viewModel.uiState.value is ModelsListUiState.ListSuccessfullyFetched)
        assertThat(viewModel.uiState.value).isEqualTo(models)
    }

    @Test
    fun `loadModels updates ui state on failure`() = runTest {
        val carSummary = CarSummary()
        val errorMessage = "Network error"
        coEvery { fetchModelsUseCase(carSummary) } returns Result.failure(Exception(errorMessage))

        viewModel.loadModels(carSummary)

        assert(viewModel.uiState.value is ModelsListUiState.ErrorOccurred)
        assertThat(viewModel.uiState.value).isEqualTo(errorMessage)
    }

    @Test
    fun `filterModels updates filteredModels state`() = runTest {
        val models = TestData.getModelAsDomainModels()
        val filterQuery = "x2"

        viewModel.filterModels(filterQuery)

        assertThat(viewModel.filteredModels.value).containsExactly(models[1])
    }
}