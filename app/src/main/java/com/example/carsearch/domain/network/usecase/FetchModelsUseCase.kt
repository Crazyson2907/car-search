package com.example.carsearch.domain.network.usecase

import com.example.carsearch.data.repository.feature.ModelsRepositoryImpl
import com.example.carsearch.domain.core.model.CarSummary
import com.example.carsearch.domain.core.model.main.Model

class FetchModelsUseCase(private val repo: ModelsRepositoryImpl) {
    suspend operator fun invoke(car: CarSummary): Result<List<Model>> {
        return repo.fetchModels(car)
    }
}