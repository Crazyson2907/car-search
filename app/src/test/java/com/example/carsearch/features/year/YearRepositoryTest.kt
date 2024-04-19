package com.example.carsearch.features.year

import com.example.carsearch.common.BaseTest
import com.example.carsearch.data.remote.RemoteDataSource
import com.example.carsearch.data.repository.feature.YearsRepositoryImpl
import com.example.carsearch.domain.core.mapper.YearMapper
import com.example.carsearch.domain.core.model.CarSummary
import com.example.carsearch.domain.core.model.dto.YearDto
import com.example.carsearch.domain.core.model.main.Manufacturer
import com.example.carsearch.domain.core.model.main.Model
import com.example.carsearch.domain.core.model.main.Year
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class YearsRepositoryTest : BaseTest() {
    private lateinit var yearsRepository: YearsRepositoryImpl
    @RelaxedMockK
    private lateinit var yearsRemoteDataSource: RemoteDataSource<YearDto>
    @RelaxedMockK
    private lateinit var yearMapper: YearMapper<List<YearDto>, List<Year>>

    @Before
    override fun setup() {
        super.setup()
        yearsRepository = YearsRepositoryImpl(yearsRemoteDataSource, yearMapper)
    }

    @Test
    fun `fetchYears maps DTOs to domain models`() = runTest {
        val carSummary = CarSummary(Manufacturer(1, "Audi"), Model("Q7", "Q7"))
        val yearDtos = listOf(YearDto("2020", "2020"))
        val years = listOf(Year(2020, "2020"))

        coEvery { yearsRemoteDataSource.doFetching(carSummary) } returns Result.success(yearDtos)
        coEvery { yearMapper.map(yearDtos) } returns years

        val result = yearsRepository.fetchYears(carSummary)

        assertThat(result).isEqualTo(Result.success(years))
    }
}