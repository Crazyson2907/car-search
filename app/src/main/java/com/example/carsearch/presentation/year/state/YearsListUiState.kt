package com.example.carsearch.presentation.year.state

import com.example.carsearch.domain.core.model.main.Year

sealed class YearsListUiState {
    data class ListSuccessfullyFetched(val list: List<Year>) : YearsListUiState()
    data class ErrorOcurred(val message: String) : YearsListUiState()
    data object Loading : YearsListUiState()
}
