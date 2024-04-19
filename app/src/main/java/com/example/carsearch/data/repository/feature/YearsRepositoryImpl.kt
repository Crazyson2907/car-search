package com.example.carsearch.data.repository.feature

import com.example.carsearch.data.remote.RemoteDataSource
import com.example.carsearch.domain.core.mapper.YearMapper
import com.example.carsearch.domain.core.model.CarSummary
import com.example.carsearch.domain.core.model.dto.YearDto
import com.example.carsearch.domain.core.model.main.Year
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class YearsRepositoryImpl @Inject constructor(
    private val yearsRemoteDataSource: RemoteDataSource<YearDto>  ,
    @Named("yearMapper")private val mapper:  YearMapper<List<YearDto>, List<Year>>,
) {
    suspend fun fetchYears(car: CarSummary): Result<List<Year>> =
        withContext(Dispatchers.IO) {
            try {
               yearsRemoteDataSource
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