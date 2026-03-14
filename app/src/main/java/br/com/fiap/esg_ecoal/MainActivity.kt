package br.com.fiap.esg_ecoal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import br.com.fiap.esg_ecoal.navigation.ScreenRoute
import br.com.fiap.esg_ecoal.navigation.SetupNavigation
import br.com.fiap.esg_ecoal.network.RetrofitClient
import br.com.fiap.esg_ecoal.repository.SettingsRepository
import br.com.fiap.esg_ecoal.repository.TokenRepository
import br.com.fiap.esg_ecoal.ui.theme.ESGEcoalTheme
import br.com.fiap.esg_ecoal.utils.LocaleManager
import dataStore
import kotlinx.coroutines.runBlocking

val LocalTokenRepository = compositionLocalOf<TokenRepository?> { null }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val settingsRepository = SettingsRepository(dataStore)
        val tokenRepository = TokenRepository(dataStore)

        // Restore token synchronously before rendering
        val hasToken = runBlocking { tokenRepository.restoreToken() } != null

        setContent {
            val temaEscuro by settingsRepository.temaFlow.collectAsState(initial = false)
            val language by settingsRepository.linguagemFlow.collectAsState(
                initial = LocaleManager.getLocale()
            )
            val context = LocalContext.current
            val updatedContext = remember(language) {
                LocaleManager.updateLocale(context, language)
            }

            CompositionLocalProvider(
                LocalContext provides updatedContext,
                LocalTokenRepository provides tokenRepository
            ) {
                ESGEcoalTheme(
                    darkTheme = temaEscuro
                ) {
                    val navController = rememberNavController()

                    // Handle 401 session expiry
                    RetrofitClient.onSessionExpired = {
                        runOnUiThread {
                            navController.navigate(ScreenRoute.Splash.route) {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    }

                    val startDestination = if (hasToken) {
                        ScreenRoute.Home.route
                    } else {
                        ScreenRoute.Splash.route
                    }

                    SetupNavigation(
                        navController = navController,
                        startDestination = startDestination
                    )
                }
            }
        }
    }
}
