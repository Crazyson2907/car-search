package com.example.carsearch.features.model

import com.example.carsearch.common.BaseTest
import com.example.carsearch.data.repository.feature.ModelsRepositoryImpl
import com.example.carsearch.domain.core.model.CarSummary
import com.example.carsearch.domain.network.usecase.FetchModelsUseCase
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ModelFetchUseCase : BaseTest() {

    @RelaxedMockK
    private lateinit var modelsRepository: ModelsRepositoryImpl
    private lateinit var fetchModelsUseCase: FetchModelsUseCase

    @Before
    override fun setup() {
        super.setup()
        MockKAnnotations.init(this)
        fetchModelsUseCase = FetchModelsUseCase(modelsRepository)
    }

    @Test
    fun `invoke calls fetchModels on repository with correct CarInfo`() = runTest {
        val carInfo = CarSummary()

        fetchModelsUseCase(carInfo)

        coVerify { modelsRepository.fetchModels(carInfo) }
    }
}