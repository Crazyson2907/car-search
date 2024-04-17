package com.example.carsearch.data.repository.feature

import com.example.carsearch.data.remote.RemoteDataSource
import com.example.carsearch.domain.core.base.BaseMapper
import com.example.carsearch.domain.core.mapper.ManufacturerMapper
import com.example.carsearch.domain.core.model.main.Manufacturer
import com.example.carsearch.domain.core.model.dto.ManufacturerDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Named

/**
 * Repository class for fetching manufacturers from a remote data source and mapping them to domain models.
 *
 * @param remoteDataSource Provides the data transfer objects for manufacturers.
 * @param dtoToDomainManufacturersMapper Maps DTOs to domain model objects.
 */
class ManufacturersRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource<ManufacturerDto>,
    @Named("manufacturerMapper")private val dtoToDomainManufacturersMapper: ManufacturerMapper<List<ManufacturerDto>, List<Manufacturer>>
) {

    /**
     * Fetches manufacturers from the remote data source and maps them to domain models.
     * Runs in the IO dispatcher to optimize threading.
     *
     * @return A Result containing a list of Manufacturer domain models or an error.
     */
    suspend fun fetchManufacturers(): Result<List<Manufacturer>> = withContext(Dispatchers.IO) {
        try {
            val apiResult = remoteDataSource.doFetching()
            apiResult.fold(
                onSuccess = { dtoList ->
                    try {
                        // Map the DTOs to domain models
                        val domainList = dtoToDomainManufacturersMapper.map(dtoList)
                        Result.success(domainList)
                    } catch (e: Exception) {
                        // Handle mapping errors or other exceptions
                        Result.failure(e)
                    }
                },
                onFailure = { error ->
                    // Directly propagate the error from the data source
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