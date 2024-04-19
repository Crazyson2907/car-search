package com.example.carsearch.features.model

import com.example.carsearch.common.BaseTest
import com.example.carsearch.data.remote.RemoteDataSource
import com.example.carsearch.data.repository.feature.ModelsRepositoryImpl
import com.example.carsearch.domain.core.mapper.ModelMapper
import com.example.carsearch.domain.core.model.CarSummary
import com.example.carsearch.domain.core.model.dto.ModelDto
import com.example.carsearch.domain.core.model.main.Manufacturer
import com.example.carsearch.domain.core.model.main.Model
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException

@RunWith(JUnit4::class)
class ModelsRepositoryTest : BaseTest() {

    @RelaxedMockK
    private lateinit var modelsMapper: ModelMapper<List<ModelDto>, List<Model>>

    @RelaxedMockK
    private lateinit var modelsRemoteDataSource: RemoteDataSource<ModelDto>

    private lateinit var modelsRepositoryImpl: ModelsRepositoryImpl

    @Before
    override fun setup() {
        super.setup()
        modelsRepositoryImpl = ModelsRepositoryImpl(modelsMapper, modelsRemoteDataSource)
    }

    @Test
    fun `fetch models successfully from remote data source`() = runTest {
        val carSummary = CarSummary(Manufacturer(id = 1, name = "Audi"))
        val modelDtos = listOf(ModelDto("Q7", "Q7"))
        val models = listOf(Model("Q7", "Q7"))

        coEvery { modelsRemoteDataSource.doFetching(carSummary) } returns Result.success(modelDtos)
        coEvery { modelsMapper.map(modelDtos) } returns models

        val result = modelsRepositoryImpl.fetchModels(carSummary)

        coVerify(exactly = 1) { modelsRemoteDataSource.doFetching(carSummary) }
        coVerify(exactly = 1) { modelsMapper.map(modelDtos) }
        assertThat(result.getOrNull()).isEqualTo(models)
    }

    @Test
    fun `handle errors when remote data source fails`() = runTest {
        val carSummary = CarSummary(Manufacturer(id = 1, name = "Audi"))
        val error = IOException("Network error")

        coEvery { modelsRemoteDataSource.doFetching(carSummary) } returns Result.failure(error)

        val result = modelsRepositoryImpl.fetchModels(carSummary)

        coVerify(exactly = 1) { modelsRemoteDataSource.doFetching(carSummary) }
        coVerify(exactly = 0) { modelsMapper.map(any()) }
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isEqualTo(error)
    }
}