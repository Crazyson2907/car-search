package com.example.carsearch.presentation.year

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carsearch.domain.core.model.CarSummary
import com.example.carsearch.domain.core.model.main.Year
import com.example.carsearch.domain.core.usecase.FetchYearsUseCase
import com.example.carsearch.presentation.year.state.YearsListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class YearViewModel @Inject constructor(
    private val fetchYearsUseCase: FetchYearsUseCase
) : ViewModel() {
    private val _years = mutableListOf<Year>()
    private val _uiState =
        MutableStateFlow<YearsListUiState>(YearsListUiState.Loading)
    val uiState: StateFlow<YearsListUiState> = _uiState.asStateFlow()

    private var isLoading = false

    fun loadYears(carSummary: CarSummary) {
        if (isLoading) return

        isLoading = true
        _uiState.value = YearsListUiState.Loading
        viewModelScope.launch {
            val result = fetchYearsUseCase(carSummary)
            isLoading = false
            result.fold(
                onSuccess = { years ->
                    _years.addAll(years)
                    _uiState.value = if (years.isNotEmpty()) {
                        YearsListUiState.ListSuccessfullyFetched(years)
                    } else {
                        YearsListUiState.ErrorOcurred("No years loaded")
                    }
                },
                onFailure = { error ->
                    _uiState.value = YearsListUiState.ErrorOcurred(error.message ?: "Unknown error")
                }
            )
        }

    }
}