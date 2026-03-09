package br.com.fiap.esg_ecoal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import br.com.fiap.esg_ecoal.navigation.SetupNavigation
import br.com.fiap.esg_ecoal.repository.SettingsRepository
import br.com.fiap.esg_ecoal.ui.theme.ESGEcoalTheme
import dataStore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val settingsRepository = SettingsRepository(dataStore)

        setContent {

            val temaEscuro by settingsRepository.temaFlow.collectAsState(initial = false)

            ESGEcoalTheme(
                darkTheme = temaEscuro
            ) {
                // Inicializa o controlador e chama o Setup
                val navController = rememberNavController()
                SetupNavigation(navController = navController)
            }
        }
    }
}