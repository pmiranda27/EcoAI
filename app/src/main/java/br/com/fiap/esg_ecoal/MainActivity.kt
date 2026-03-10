package br.com.fiap.esg_ecoal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import br.com.fiap.esg_ecoal.navigation.SetupNavigation
import br.com.fiap.esg_ecoal.repository.SettingsRepository
import br.com.fiap.esg_ecoal.ui.theme.ESGEcoalTheme
import br.com.fiap.esg_ecoal.utils.LocaleManager
import dataStore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val settingsRepository = SettingsRepository(dataStore)

        setContent {
            val temaEscuro by settingsRepository.temaFlow.collectAsState(initial = false)
            val language by settingsRepository.linguagemFlow.collectAsState(
                initial = "pt"
            )
            val context = LocalContext.current
            val updatedContext = remember(language) {
                LocaleManager.updateLocale(context, language)
            }

            CompositionLocalProvider(
                LocalContext provides updatedContext
            ) {
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
}