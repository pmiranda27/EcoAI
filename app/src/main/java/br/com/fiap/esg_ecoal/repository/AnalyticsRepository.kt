package br.com.fiap.esg_ecoal.repository

import br.com.fiap.esg_ecoal.data.model.DimensionsResponse
import br.com.fiap.esg_ecoal.data.model.ScoreResponse
import br.com.fiap.esg_ecoal.network.RetrofitClient

class AnalyticsRepository {

    private val api = RetrofitClient.apiService

    suspend fun getDimensions(period: String? = null): Result<DimensionsResponse> {
        return safeApiCall { api.getDimensions(period) }
    }

    suspend fun getScore(): Result<ScoreResponse> {
        return safeApiCall { api.getScore() }
    }
}
