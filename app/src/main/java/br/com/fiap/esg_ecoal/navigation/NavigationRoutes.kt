package br.com.fiap.esg_ecoal.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.fiap.esg_ecoal.ui.screens.SplashScreen
import br.com.fiap.esg_ecoal.ui.screens.settings.IdiomaScreen
import br.com.fiap.esg_ecoal.ui.screens.settings.SettingsScreen

@Composable
fun SetupNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.Settings.route
    ) {
        // Rota da Splash: Ela gerencia internamente as gavetas de Login e Cadastro
        composable(route = ScreenRoute.Splash.route) {
            SplashScreen(
                onNavigateToLogin = {
                    // Navega para a Home após o sucesso do Login na gaveta
                    navController.navigate(ScreenRoute.Home.route) {
                        popUpTo(ScreenRoute.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToSignUp = {
                    // Navega para a Home (ou uma tela de boas-vindas) após o Cadastro
                    navController.navigate(ScreenRoute.Home.route) {
                        popUpTo(ScreenRoute.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        // Rota para a tela principal do sistema (Dashboard ESG)
        composable(route = ScreenRoute.Home.route) {
        }

        composable(route = ScreenRoute.Settings.route){
            SettingsScreen(navController = navController)
        }

        composable(route = ScreenRoute.Idiomas.route){
            IdiomaScreen(navController)
        }
    }
}