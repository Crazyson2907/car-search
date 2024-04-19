package com.example.carsearch.presentation.manufacturers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carsearch.domain.core.model.main.Manufacturer
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
    private val _manufacturers = mutableListOf<Manufacturer>()
    private val _uiState =
        MutableStateFlow<ManufacturersListUiState>(ManufacturersListUiState.Loading)
    val uiState: StateFlow<ManufacturersListUiState> = _uiState.asStateFlow()

    private val _selectedAuto = MutableStateFlow<Manufacturer?>(null)
    val selectedAuto: StateFlow<Manufacturer?> = _selectedAuto.asStateFlow()

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
                        isLastPage = true
                        _uiState.value = ManufacturersListUiState.ListSuccessfullyFetched(_manufacturers.toList())
                    } else {
                        _manufacturers.addAll(newManufacturers)
                        _uiState.value = ManufacturersListUiState.ListSuccessfullyFetched(_manufacturers.toList())
                    }
                },
                onFailure = { error ->
                    _uiState.value =
                        ManufacturersListUiState.ErrorOccurred(error.message ?: "Unknown error")
                }
            )
        }
    }

    fun selectAuto(auto: Manufacturer) {
        _selectedAuto.value = auto
    }
}