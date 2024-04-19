package com.example.carsearch.presentation.types

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carsearch.domain.core.model.CarSummary
import com.example.carsearch.domain.core.model.main.Model
import com.example.carsearch.domain.network.usecase.FetchModelsUseCase
import com.example.carsearch.presentation.types.state.ModelsListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ModelViewModel @Inject constructor(
    private val fetchModelsUseCase: FetchModelsUseCase
) : ViewModel() {
    private val _models = mutableListOf<Model>()
    private val _uiState = MutableStateFlow<ModelsListUiState>(ModelsListUiState.Loading)
    val uiState: StateFlow<ModelsListUiState> = _uiState.asStateFlow()


    private val _filteredModels = MutableStateFlow<List<Model>>(emptyList())
    val filteredModels: StateFlow<List<Model>> = _filteredModels.asStateFlow()

    private var isLoading = false


    fun loadModels(carSummary: CarSummary) {
        if (isLoading) return

        isLoading = true
        _uiState.value = ModelsListUiState.Loading
        viewModelScope.launch {
            val result = fetchModelsUseCase(carSummary)
            isLoading = false
            result.fold(
                onSuccess = { models ->
                    _models.addAll(models)
                    _filteredModels.value = _models
                    _uiState.value = if (models.isNotEmpty()) {
                        ModelsListUiState.ListSuccessfullyFetched(models)
                    } else {
                        ModelsListUiState.ErrorOccurred("No models found")
                    }
                },
                onFailure = { error ->
                    _uiState.value = ModelsListUiState.ErrorOccurred(error.message ?: "Unknown error")
                }
            )
        }
    }

    fun filterModels(query: String) {
        if (query.isEmpty()) {
            _filteredModels.value = _models
        } else {
            _filteredModels.value = _models.filter {
                it.name.lowercase(Locale.getDefault()).contains(query.lowercase(Locale.getDefault()))
            }
        }
    }

}