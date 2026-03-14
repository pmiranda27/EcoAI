package br.com.fiap.esg_ecoal.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import br.com.fiap.esg_ecoal.network.RetrofitClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class TokenRepository(private val dataStore: DataStore<Preferences>) {

    private val TOKEN_KEY = stringPreferencesKey("auth_token")
    private val USER_NAME_KEY = stringPreferencesKey("user_name")
    private val USER_EMAIL_KEY = stringPreferencesKey("user_email")

    val userName: Flow<String?> = dataStore.data.map { it[USER_NAME_KEY] }
    val userEmail: Flow<String?> = dataStore.data.map { it[USER_EMAIL_KEY] }

    suspend fun saveToken(token: String) {
        RetrofitClient.token = token
        dataStore.edit { it[TOKEN_KEY] = token }
    }

    suspend fun saveUser(name: String, email: String) {
        dataStore.edit {
            it[USER_NAME_KEY] = name
            it[USER_EMAIL_KEY] = email
        }
    }

    suspend fun restoreToken(): String? {
        val token = dataStore.data.first()[TOKEN_KEY]
        if (token != null) {
            RetrofitClient.token = token
        }
        return token
    }

    suspend fun clearAll() {
        RetrofitClient.token = null
        dataStore.edit {
            it.remove(TOKEN_KEY)
            it.remove(USER_NAME_KEY)
            it.remove(USER_EMAIL_KEY)
        }
    }
}
