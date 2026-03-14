package br.com.fiap.esg_ecoal.repository

import br.com.fiap.esg_ecoal.data.model.SignInRequest
import br.com.fiap.esg_ecoal.data.model.SignUpRequest
import br.com.fiap.esg_ecoal.data.model.SignUpResponse
import br.com.fiap.esg_ecoal.network.RetrofitClient

class AuthRepository(private val tokenRepository: TokenRepository) {

    private val api = RetrofitClient.apiService

    suspend fun signIn(email: String, password: String): Result<Unit> {
        val result = safeApiCall { api.signIn(SignInRequest(email, password)) }
        return result.fold(
            onSuccess = { response ->
                tokenRepository.saveToken(response.token)
                // Fetch user info from /me, fallback to email if it fails
                val meResult = safeApiCall { api.getMe() }
                meResult.fold(
                    onSuccess = { user ->
                        tokenRepository.saveUser(user.name, user.email)
                    },
                    onFailure = {
                        tokenRepository.saveUser(email.substringBefore("@"), email)
                    }
                )
                Result.success(Unit)
            },
            onFailure = { Result.failure(it) }
        )
    }

    suspend fun signUp(
        name: String,
        email: String,
        password: String,
        cnpj: String
    ): Result<SignUpResponse> {
        return safeApiCall {
            api.signUp(SignUpRequest(name, email, password, role = "member", cnpj = cnpj))
        }
    }

    suspend fun logout() {
        tokenRepository.clearAll()
    }
}
