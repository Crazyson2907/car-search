package com.example.carsearch.features.year

import com.example.carsearch.common.BaseTest
import com.example.carsearch.domain.core.mapper.YearMapper
import com.example.carsearch.domain.core.model.dto.YearDto
import com.example.carsearch.domain.core.model.main.Year
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class YearMappingTest : BaseTest() {
    private lateinit var yearMapper: YearMapper<List<YearDto>, List<Year>>

    @Before
    override fun setup() {
        super.setup()
        yearMapper = YearMapper()
    }

    @Test
    fun `map returns a list of Year domain models`() = runTest {
        val yearDtos = listOf(YearDto("2020", "2020"))
        val expected = listOf(Year(2020, "2020"))

        val actual = yearMapper.map(yearDtos)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `return empty list when input is empty`() = runTest {
        val actualYears = yearMapper.map(emptyList())
        assertThat(actualYears).isEmpty()
    }
}