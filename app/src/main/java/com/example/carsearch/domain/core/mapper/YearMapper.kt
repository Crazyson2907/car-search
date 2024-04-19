package com.example.carsearch.domain.core.mapper

import com.example.carsearch.domain.core.base.BaseMapper
import com.example.carsearch.domain.core.model.dto.YearDto
import com.example.carsearch.domain.core.model.main.Year
import javax.inject.Inject

class YearMapper<T, U>@Inject constructor() : BaseMapper<List<YearDto>, List<Year>> {
    override suspend fun map(input: List<YearDto>): List<Year> {
        return input.map { dto ->
            try {
                Year(dto.id.toInt(), dto.id)
            } catch (e: NumberFormatException) {
                // Log the error or handle it as necessary
                throw IllegalArgumentException("Invalid year ID '${dto.id}' in DTO: ${e.message}", e)
            }
        }
    }
}