package com.example.carsearch.presentation.manufacturers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carsearch.domain.core.usecase.FetchManufacturersUseCase
import com.example.carsearch.presentation.manufacturers.state.ManufacturersListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManufacturerViewModel @Inject constructor(
    private val fetchManufacturersUseCase: FetchManufacturersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ManufacturersListUiState>(ManufacturersListUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadManufacturers()
    }

    fun loadManufacturers() {
        viewModelScope.launch {
            _uiState.value = ManufacturersListUiState.Loading
            val result = fetchManufacturersUseCase()
            result.fold(
                onSuccess = { manufacturers ->
                    if (manufacturers.isNotEmpty()) {
                        _uiState.value = ManufacturersListUiState.ListSuccessfullyFetched(manufacturers)
                    } else {
                        _uiState.value = ManufacturersListUiState.ErrorOccurred("No manufacturers found")
                    }
                },
                onFailure = { error ->
                    _uiState.value = ManufacturersListUiState.ErrorOccurred(error.message ?: "Unknown error")
                }
            )
        }
    }
}