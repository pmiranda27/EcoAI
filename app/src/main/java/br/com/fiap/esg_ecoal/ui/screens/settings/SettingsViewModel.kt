package br.com.fiap.esg_ecoal.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.esg_ecoal.repository.SettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {


    val theme: StateFlow<Boolean> = settingsRepository.temaFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        false
    )

    fun changeTheme(darkTheme: Boolean) {
        viewModelScope.launch {
            settingsRepository.setTheme(darkTheme)
        }
    }
}