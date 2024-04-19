package com.example.carsearch.domain.core.usecase

import com.example.carsearch.data.repository.feature.YearsRepositoryImpl
import com.example.carsearch.domain.core.model.CarSummary
import com.example.carsearch.domain.core.model.main.Year

class FetchYearsUseCase(private val repo: YearsRepositoryImpl) {
    suspend operator fun invoke(car: CarSummary): Result<List<Year>> {
        return repo.fetchYears(car)
    }
}