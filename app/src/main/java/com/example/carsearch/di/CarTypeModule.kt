package com.example.carsearch.di

import com.example.carsearch.data.remote.RemoteDataSource
import com.example.carsearch.data.remote.features.ModelsDataSource
import com.example.carsearch.data.repository.feature.ModelsRepositoryImpl
import com.example.carsearch.domain.core.mapper.ModelMapper
import com.example.carsearch.domain.core.model.dto.ModelDto
import com.example.carsearch.domain.core.model.main.Model
import com.example.carsearch.domain.network.usecase.FetchModelsUseCase
import com.example.carsearch.domain.network.core.features.ModelsApiService
import com.example.carsearch.presentation.types.ModelViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ActivityRetainedComponent::class)
object ModelModule {

    @Provides
    @Named("modelMapper")
    fun provideModelMapper(): ModelMapper<List<ModelDto>, List<Model>> = ModelMapper()

    @Provides
    fun provideModelRemoteApi(retrofit: Retrofit): ModelsApiService =
        retrofit.create(ModelsApiService::class.java)

    @Provides
    fun provideModelRemoteDataSource(api: ModelsApiService): RemoteDataSource<ModelDto> =
        ModelsDataSource(api)

    @Provides
    fun provideModelsRepository(
        mapper: ModelMapper<List<ModelDto>, List<Model>>,
        dataSource: RemoteDataSource<ModelDto>,
    ): ModelsRepositoryImpl = ModelsRepositoryImpl(mapper, dataSource)

    @Provides
    fun provideFetchModelsUseCase(repository: ModelsRepositoryImpl): FetchModelsUseCase =
        FetchModelsUseCase(repository)

    @Provides
    fun provideModelViewModel(useCase: FetchModelsUseCase): ModelViewModel =
        ModelViewModel(useCase)
}