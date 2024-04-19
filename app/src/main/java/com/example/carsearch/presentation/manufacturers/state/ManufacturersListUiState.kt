package com.example.carsearch.presentation.manufacturers.state

import com.example.carsearch.domain.core.model.main.Manufacturer

sealed class ManufacturersListUiState {
    data class ListSuccessfullyFetched(val list: List<Manufacturer>) : ManufacturersListUiState()
    data class ErrorOccurred(val message: String) : ManufacturersListUiState()
    data class NoMoreData(val message: String) : ManufacturersListUiState()
    object Loading : ManufacturersListUiState()
}