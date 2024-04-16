package com.example.carsearch.features.manufacturer

import com.example.carsearch.common.BaseTest
import com.example.carsearch.common.TestData
import com.example.carsearch.domain.core.mapper.ManufacturersMapper
import com.example.carsearch.domain.core.model.dto.ManufacturerDto
import com.example.carsearch.domain.core.model.main.Manufacturer
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ManufacturersMapperShould : BaseTest() {
    private lateinit var manufacturersMapper: ManufacturersMapper

    @Before
    override fun setup() {
        super.setup()
        manufacturersMapper = ManufacturersMapper()
    }

    @Test
    fun `return no manufacturers when input is empty`() = runTest {
        val data = emptyList<ManufacturerDto>()
        val actual = manufacturersMapper.map(data)
        assertThat(actual).isEmpty()
    }

    @Test
    fun `return one manufacturer when input contains one dto`() = runTest {
        val singleDto = listOf(ManufacturerDto("60", "Audi"))
        val expected = listOf(Manufacturer(60, "Audi"))

        val actual = manufacturersMapper.map(singleDto)

        assertThat(actual).containsExactlyElementsIn(expected).inOrder()
    }

    @Test
    fun `return many manufacturers when input contains multiple dtos`() = runTest {
        val data = TestData.getManufacturersAsDto()
        val actual = manufacturersMapper.map(data)

        assertThat(actual).containsExactlyElementsIn(TestData.getManufacturersAsDomainModels()).inOrder()
    }
}