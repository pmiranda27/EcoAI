package br.com.fiap.esg_ecoal.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.esg_ecoal.data.model.DimensionsResponse
import br.com.fiap.esg_ecoal.data.model.ScoreResponse
import br.com.fiap.esg_ecoal.data.model.UiState
import br.com.fiap.esg_ecoal.repository.AnalyticsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val analyticsRepository: AnalyticsRepository) : ViewModel() {

    private val _dimensions = MutableStateFlow<UiState<DimensionsResponse>>(UiState.Loading)
    val dimensions: StateFlow<UiState<DimensionsResponse>> = _dimensions

    private val _score = MutableStateFlow<UiState<ScoreResponse>>(UiState.Loading)
    val score: StateFlow<UiState<ScoreResponse>> = _score

    private var loadJob: Job? = null

    fun loadData(timeframe: String? = null) {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            _dimensions.value = UiState.Loading
            _score.value = UiState.Loading

            val period = when (timeframe) {
                "Mensal" -> "monthly"
                "Trimestral" -> "quarterly"
                "Anual" -> "annual"
                else -> null
            }

            val dimResult = analyticsRepository.getDimensions(period)
            _dimensions.value = dimResult.fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message ?: "Erro ao carregar dimensões") }
            )

            val scoreResult = analyticsRepository.getScore()
            _score.value = scoreResult.fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message ?: "Erro ao carregar score") }
            )
        }
    }
}
