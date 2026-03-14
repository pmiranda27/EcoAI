package br.com.fiap.esg_ecoal.data.model

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String, val statusCode: Int? = null) : UiState<Nothing>()
}
