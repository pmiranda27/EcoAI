package br.com.fiap.esg_ecoal.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.esg_ecoal.data.model.UiState
import br.com.fiap.esg_ecoal.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _signInState = MutableStateFlow<UiState<Unit>?>(null)
    val signInState: StateFlow<UiState<Unit>?> = _signInState

    private val _signUpState = MutableStateFlow<UiState<Unit>?>(null)
    val signUpState: StateFlow<UiState<Unit>?> = _signUpState

    fun signIn(email: String, password: String) {
        _signInState.value = UiState.Loading
        viewModelScope.launch {
            val result = authRepository.signIn(email, password)
            _signInState.value = result.fold(
                onSuccess = { UiState.Success(Unit) },
                onFailure = { UiState.Error(it.message ?: "Erro ao fazer login") }
            )
        }
    }

    fun signUp(name: String, email: String, password: String, cnpj: String) {
        _signUpState.value = UiState.Loading
        viewModelScope.launch {
            val signUpResult = authRepository.signUp(name, email, password, cnpj)
            signUpResult.fold(
                onSuccess = {
                    // Auto sign-in after successful sign-up
                    val signInResult = authRepository.signIn(email, password)
                    _signUpState.value = signInResult.fold(
                        onSuccess = { UiState.Success(Unit) },
                        onFailure = { UiState.Error(it.message ?: "Conta criada, mas erro ao entrar") }
                    )
                },
                onFailure = {
                    _signUpState.value = UiState.Error(it.message ?: "Erro ao criar conta")
                }
            )
        }
    }

    fun resetStates() {
        _signInState.value = null
        _signUpState.value = null
    }
}
