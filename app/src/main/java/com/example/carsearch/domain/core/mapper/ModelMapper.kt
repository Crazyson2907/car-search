package com.example.carsearch.domain.core.mapper

import com.example.carsearch.domain.core.base.BaseMapper
import com.example.carsearch.domain.core.model.dto.ModelDto
import com.example.carsearch.domain.core.model.main.Model
import javax.inject.Inject

class ModelMapper<T, U>@Inject constructor() : BaseMapper<List<ModelDto>,List<Model>> {
    override suspend fun map(input: List<ModelDto>): List<Model> {
        return input.map { Model(it.modelId, it.modelName) }
    }
}