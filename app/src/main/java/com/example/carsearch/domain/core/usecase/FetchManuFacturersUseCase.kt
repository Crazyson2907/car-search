package com.example.carsearch.domain.core.usecase

import com.example.carsearch.data.repository.feature.ManufacturersRepositoryImpl
import com.example.carsearch.domain.core.model.main.Manufacturer

/**
 * Use case that manages the retrieval of manufacturers data.
 * It abstracts the logic for fetching manufacturers from the data layer.
 *
 * @param manufacturersRepo Repository responsible for fetching manufacturer data.
 */
class FetchManufacturersUseCase(private val manufacturersRepo: ManufacturersRepositoryImpl) {
    suspend operator fun invoke(): Result<List<Manufacturer>> {
        return manufacturersRepo.fetchManufacturers()
    }
}