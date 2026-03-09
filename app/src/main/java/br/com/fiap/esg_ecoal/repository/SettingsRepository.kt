package br.com.fiap.esg_ecoal.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepository(private val dataStore: DataStore<Preferences>) {
    private val TEMA_ESCURO = booleanPreferencesKey("tema_escuro")

    val temaFlow: Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[TEMA_ESCURO] ?: false
    }

    suspend fun setTheme(theme: Boolean){
        dataStore.edit { prefs ->
            prefs[TEMA_ESCURO] = theme
        }
    }
}