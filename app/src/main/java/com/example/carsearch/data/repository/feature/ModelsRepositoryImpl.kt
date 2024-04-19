package com.example.carsearch.data.repository.feature

import com.example.carsearch.data.remote.RemoteDataSource
import com.example.carsearch.domain.core.base.BaseMapper
import com.example.carsearch.domain.core.mapper.ModelMapper
import com.example.carsearch.domain.core.model.CarSummary
import com.example.carsearch.domain.core.model.dto.ModelDto
import com.example.carsearch.domain.core.model.main.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class ModelsRepositoryImpl @Inject constructor(
    @Named("modelMapper")private val mapper: ModelMapper<List<ModelDto>, List<Model>>,
    private val modelsRemoteDataSource: RemoteDataSource<ModelDto>,
) {
    suspend fun fetchModels(car: CarSummary): Result<List<Model>> =
        withContext(Dispatchers.IO) {
            try {
                modelsRemoteDataSource
                    .doFetching(car)
                    .fold(
                        onSuccess = { dtoList ->
                            try {
                                val domainList = mapper.map(dtoList)
                                Result.success(domainList)
                            } catch (e: Exception) {
                                Result.failure(e)
                            }
                        },
                        onFailure = { error ->
                            Result.failure(error)
                        }
                    )
            } catch (e: HttpException) {
                // Handle specific HTTP errors if necessary
                Result.failure(e)
            } catch (e: Exception) {
                // Handle other exceptions such as network issues
                Result.failure(e)
            }
        }
}