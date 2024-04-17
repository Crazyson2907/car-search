package com.example.carsearch.di

import com.example.carsearch.data.remote.RemoteDataSource
import com.example.carsearch.data.remote.features.ManufacturersRemoteDataSource
import com.example.carsearch.data.repository.feature.ManufacturersRepositoryImpl
import com.example.carsearch.data.repository.paging.PagingManager
import com.example.carsearch.domain.core.mapper.ManufacturerMapper
import com.example.carsearch.domain.core.model.dto.ManufacturerDto
import com.example.carsearch.domain.core.model.main.Manufacturer
import com.example.carsearch.domain.core.usecase.FetchManufacturersUseCase
import com.example.carsearch.domain.network.core.features.ManufacturersApiService
import com.example.carsearch.presentation.manufacturers.ManufacturerViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ActivityRetainedComponent::class)
object ManufacturerModule {

    @Provides
    fun providePagingManager(): PagingManager = PagingManager(15)

    @Provides
    @Named("manufacturerMapper")
    fun provideManufacturerMapper(): ManufacturerMapper<List<ManufacturerDto>, List<Manufacturer>> =
        ManufacturerMapper()

    @Provides
    @Named("manufacturerRemoteDataSource")
    fun provideManufacturerRemoteDataSource(
        api: ManufacturersApiService,
        pagingManager: PagingManager
    ): RemoteDataSource<ManufacturerDto> =
        ManufacturersRemoteDataSource(api, pagingManager)

    @Provides
    fun provideManufacturersApi(retrofit: Retrofit): ManufacturersApiService =
        retrofit.create(ManufacturersApiService::class.java)

    @Provides
    fun provideManufacturersRepo(
        @Named("manufacturerRemoteDataSource") remoteDataSource: RemoteDataSource<ManufacturerDto>,
        @Named("manufacturerMapper") mapper: ManufacturerMapper<List<ManufacturerDto>, List<Manufacturer>>
    ): ManufacturersRepositoryImpl = ManufacturersRepositoryImpl(remoteDataSource, mapper)

    @Provides
    fun provideFetchManufacturersUseCase(repo: ManufacturersRepositoryImpl): FetchManufacturersUseCase =
        FetchManufacturersUseCase(repo)

    @Provides
    fun provideCarManufacturersViewModel(useCase: FetchManufacturersUseCase): ManufacturerViewModel =
        ManufacturerViewModel(useCase)
}