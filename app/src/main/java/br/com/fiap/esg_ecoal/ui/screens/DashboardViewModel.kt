package br.com.fiap.esg_ecoal.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.esg_ecoal.data.model.DashboardData
import br.com.fiap.esg_ecoal.data.model.UiState
import br.com.fiap.esg_ecoal.repository.AnalyticsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val repository: AnalyticsRepository = AnalyticsRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<DashboardData>>(UiState.Loading)
    val uiState: StateFlow<UiState<DashboardData>> = _uiState

    init {
        loadDashboardData()
    }

    fun loadDashboardData() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            val result = repository.getFullDashboard()

            result.onSuccess { data ->
                _uiState.value = UiState.Success(data)
            }.onFailure {
                _uiState.value = UiState.Error("Erro ao carregar dados do Dashboard")
            }
        }
    }
}