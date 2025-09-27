package com.calyrsoft.ucbp1.features.dollar.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calyrsoft.ucbp1.features.dollar.data.datasource.DollarLocalDataSource
import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel
import com.calyrsoft.ucbp1.features.dollar.domain.usecase.FetchDollarUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DollarViewModel(
    private val fetchDollarUseCase: FetchDollarUseCase,
    private val localDataSource: DollarLocalDataSource
) : ViewModel() {

    sealed class DollarUIState {
        object Loading : DollarUIState()
        class Error(val message: String) : DollarUIState()
        class Success(val data: DollarModel, val history: List<DollarModel>) : DollarUIState()
    }

    private val _uiState = MutableStateFlow<DollarUIState>(DollarUIState.Loading)
    val uiState: StateFlow<DollarUIState> = _uiState.asStateFlow()

    init {
        getDollar()
        loadHistory()
    }

    fun getDollar() {
        viewModelScope.launch {
            _uiState.value = DollarUIState.Loading

            try {
                // Obtener datos actualizados (con los 4 campos nuevos)
                val dollarRates = fetchDollarUseCase()

                // Guardar en base de datos local para histórico
                localDataSource.saveDollarRate(dollarRates)

                // Cargar historial actualizado
                val history = localDataSource.getDollarHistory()

                _uiState.value = DollarUIState.Success(dollarRates, history)

            } catch (e: Exception) {
                // Intentar cargar desde base de datos local como fallback
                loadFromLocalFallback(e)
            }
        }
    }

    fun loadHistory() {
        viewModelScope.launch {
            try {
                val history = localDataSource.getDollarHistory()
                val currentData = when (val currentState = _uiState.value) {
                    is DollarUIState.Success -> currentState.data
                    else -> createEmptyDollarModel()
                }
                _uiState.value = DollarUIState.Success(currentData, history)
            } catch (e: Exception) {
                // Mantener el estado actual si falla la carga del histórico
                if (_uiState.value is DollarUIState.Error) {
                    _uiState.value = DollarUIState.Error("Error al cargar histórico: ${e.message}")
                }
            }
        }
    }

    fun refreshData() {
        getDollar()
    }

    private suspend fun loadFromLocalFallback(originalError: Exception) {
        try {
            // Intentar cargar último dato guardado localmente
            val localRates = localDataSource.getLatestDollarRate()
            val history = localDataSource.getDollarHistory()

            if (localRates != null) {
                _uiState.value = DollarUIState.Success(localRates, history)
            } else {
                _uiState.value = DollarUIState.Error(
                    "Error al cargar datos: ${originalError.message ?: "Sin conexión"}"
                )
            }
        } catch (e: Exception) {
            _uiState.value = DollarUIState.Error(
                "Error: No hay datos disponibles. ${e.message ?: "Verifica tu conexión"}"
            )
        }
    }

    private fun createEmptyDollarModel(): DollarModel {
        return DollarModel(
            dollarOfficialBuy = "0",
            dollarOfficialSell = "0",
            dollarParallelBuy = "0",
            dollarParallelSell = "0",
            lastUpdate = "No disponible",
            timestamp = 0L
        )
    }
}