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

                // Busca informações do usuário
                val meResult = safeApiCall { api.getMe() }
                meResult.fold(
                    onSuccess = { user ->
                        val cnpjParaSalvar = if (user.cnpj.isNullOrBlank()) "12345678000100" else user.cnpj

                        tokenRepository.saveUser(
                            user.name,
                            user.email,
                            cnpjParaSalvar
                        )
                    },
                    onFailure = {
                        tokenRepository.saveUser(
                            email.substringBefore("@"),
                            email,
                            "12345678000100"
                        )
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