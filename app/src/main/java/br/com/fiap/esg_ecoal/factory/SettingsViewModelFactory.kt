package br.com.fiap.esg_ecoal.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.esg_ecoal.repository.SettingsRepository
import br.com.fiap.esg_ecoal.ui.screens.settings.SettingsViewModel

class SettingsViewModelFactory(
    private val repository: SettingsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SettingsViewModel(repository) as T
    }
}