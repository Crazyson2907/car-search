package com.example.carsearch.features.model

import com.example.carsearch.common.BaseTest
import com.example.carsearch.domain.core.mapper.ModelMapper
import com.example.carsearch.domain.core.model.dto.ModelDto
import com.example.carsearch.domain.core.model.main.Model
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ModelsMapping : BaseTest() {
    private lateinit var modelMapper: ModelMapper<List<ModelDto>, List<Model>>

    @Before
    override fun setup() {
        super.setup()
        modelMapper = ModelMapper()
    }

    @Test
    fun `map ModelDto to Model domain objects`() = runTest {
        val modelDtos = listOf(ModelDto("Q7", "Q7"))
        val expectedModels = listOf(Model("Q7", "Q7"))

        val actualModels = modelMapper.map(modelDtos)

        assertThat(actualModels).isEqualTo(expectedModels)
    }

    @Test
    fun `return empty list when input is empty`() = runTest {
        val actualModels = modelMapper.map(emptyList())
        assertThat(actualModels).isEmpty()
    }
}