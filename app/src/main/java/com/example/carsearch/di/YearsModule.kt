package com.example.carsearch.di

import com.example.carsearch.data.remote.RemoteDataSource
import com.example.carsearch.data.remote.features.YearDataSource
import com.example.carsearch.data.repository.feature.YearsRepositoryImpl
import com.example.carsearch.domain.core.mapper.YearMapper
import com.example.carsearch.domain.core.model.dto.YearDto
import com.example.carsearch.domain.core.model.main.Year
import com.example.carsearch.domain.core.usecase.FetchYearsUseCase
import com.example.carsearch.domain.network.core.features.YearApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ActivityRetainedComponent::class)
object CarYearModule {

    @Provides
    @Named("yearMapper")
    fun provideYearMapper(): YearMapper<List<YearDto>, List<Year>> = YearMapper()

    @Provides
    fun provideYearsRemoteApi(retrofit: Retrofit): YearApiService =
        retrofit.create(YearApiService::class.java)

    @Provides
    fun provideYearsRemoteDataSource(api: YearApiService): RemoteDataSource<YearDto> =
        YearDataSource(api)

    @Provides
    fun provideYearsRepository(
        remoteDataSource: RemoteDataSource<YearDto>,
        mapper: YearMapper<List<YearDto>, List<Year>>
    ): YearsRepositoryImpl = YearsRepositoryImpl(remoteDataSource, mapper)

    @Provides
    fun provideFetchYearsUseCase(repository: YearsRepositoryImpl): FetchYearsUseCase =
        FetchYearsUseCase(repository)
}