package br.com.fiap.esg_ecoal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.esg_ecoal.navigation.SetupNavigation
import br.com.fiap.esg_ecoal.ui.screens.SplashScreen
import br.com.fiap.esg_ecoal.ui.theme.ESGEcoalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ESGEcoalTheme(
                darkTheme = false // por enquanto
            ) {
                // Inicializa o controlador e chama o Setup
                val navController = rememberNavController()
                SetupNavigation(navController = navController)

            }
        }
    }
}