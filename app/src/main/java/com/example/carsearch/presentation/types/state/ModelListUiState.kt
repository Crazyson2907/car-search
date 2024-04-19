package com.example.carsearch.presentation.types.state

import com.example.carsearch.domain.core.model.main.Model

sealed class ModelsListUiState {
    data class ListSuccessfullyFetched(val list: List<Model>) : ModelsListUiState()
    data class ErrorOccurred(val message: String) : ModelsListUiState()
    data object Loading : ModelsListUiState()
}