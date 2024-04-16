package com.example.carsearch.domain.core.mapper

import com.example.carsearch.domain.core.base.BaseMapper
import com.example.carsearch.domain.core.model.main.Manufacturer
import com.example.carsearch.domain.core.model.dto.ManufacturerDto

class ManufacturersMapper : BaseMapper<List<ManufacturerDto>, List<Manufacturer>> {

    /**
     * Maps the input DTOs to domain models.
     *
     * @param input List of ManufacturerDto objects to be transformed.
     * @return List of Manufacturer domain models.
     * @throws NumberFormatException if manufacturerId cannot be converted to Int.
     */
    override suspend fun map(input: List<ManufacturerDto>): List<Manufacturer> {
        return input.map { dto ->
            try {
                Manufacturer(dto.manufacturerId.toInt(), dto.manufacturerName)
            } catch (e: NumberFormatException) {
                // Log the error or handle it as necessary
                throw IllegalArgumentException("Invalid manufacturer ID '${dto.manufacturerId}' in DTO: ${e.message}", e)
            }
        }
    }
}