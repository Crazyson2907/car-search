package com.example.carsearch.presentation.manufacturers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carsearch.domain.core.model.main.Manufacturer
import com.example.carsearch.domain.network.usecase.FetchManufacturersUseCase
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
    private val _manufacturers = mutableListOf<Manufacturer>()
    private val _uiState =
        MutableStateFlow<ManufacturersListUiState>(ManufacturersListUiState.Loading)
    val uiState: StateFlow<ManufacturersListUiState> = _uiState.asStateFlow()

    var isLastPage = false
    private var isLoading = false

    init {
        loadManufacturers()
    }

    fun loadManufacturers() {
        if (isLastPage || isLoading) return
        isLoading = true
        viewModelScope.launch {
            val result = fetchManufacturersUseCase()
            isLoading = false
            result.fold(
                onSuccess = { newManufacturers ->
                    if (newManufacturers.isEmpty()) {
                        if (_manufacturers.isNotEmpty()) {
                            // If we already have data, just mark that we reached the last page
                            // and don't send any state updates to the UI
                            isLastPage = true
                        } else {
                            // If there's no data at all, it means we are at the start and there's nothing to show
                            _uiState.value =
                                ManufacturersListUiState.ErrorOccurred("No manufacturers found")
                        }
                    } else {
                        _manufacturers.addAll(newManufacturers)
                        _uiState.value =
                            ManufacturersListUiState.ListSuccessfullyFetched(_manufacturers.toList())
                    }
                },
                onFailure = { error ->
                    // Only update the UI state if an error occurs and we have not any manufacturers loaded
                    if (_manufacturers.isEmpty()) {
                        _uiState.value =
                            ManufacturersListUiState.ErrorOccurred(error.message ?: "Unknown error")
                    }
                    // If we already have manufacturers loaded, we might choose not to update the UI state,
                    // depending on how you want to handle this case.
                }
            )
        }
    }
}