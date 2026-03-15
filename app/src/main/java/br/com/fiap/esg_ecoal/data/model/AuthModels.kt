package br.com.fiap.esg_ecoal.data.model

import com.google.gson.annotations.SerializedName

data class SignInRequest(
    val email: String,
    val password: String
)

data class SignInResponse(
    val token: String
)

data class SignUpRequest(
    val name: String,
    val email: String,
    val password: String,
    val role: String,
    val cnpj: String
)

data class SignUpResponse(
    val id: Int,
    val name: String,
    val email: String,
    val role: String,
    @SerializedName("company_id") val companyId: Int,
    val cnpj: String? = null
)

data class UserResponse(
    val id: Int,
    val name: String,
    val email: String,
    val role: String,
    @SerializedName("company_id") val companyId: Int,
    val cnpj: String? = null
)
