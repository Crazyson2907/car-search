package com.example.carsearch.presentation.manufacturers

import androidx.lifecycle.ViewModel
import com.example.carsearch.domain.core.usecase.FetchManufacturersUseCase
import com.example.carsearch.presentation.manufacturers.state.ManufacturersListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ManufacturerViewModel(
    private val fetchManufacturersUseCase: FetchManufacturersUseCase
) : ViewModel() {

    private val _manufacturersListState: MutableStateFlow<ManufacturersListUiState> =
        MutableStateFlow(ManufacturersListUiState.Loading)
    val manufacturerListState: StateFlow<ManufacturersListUiState> = _manufacturersListState


    fun loadManufacturers() {

    }
}