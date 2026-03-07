package br.com.fiap.esg_ecoal.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.fiap.esg_ecoal.ui.screens.HomeScreen
import br.com.fiap.esg_ecoal.ui.screens.SplashScreen
import br.com.fiap.esg_ecoal.ui.screens.settings.IdiomaScreen
import br.com.fiap.esg_ecoal.ui.screens.settings.ProgressoSettingScreen
import br.com.fiap.esg_ecoal.ui.screens.settings.SettingsScreen
import br.com.fiap.esg_ecoal.ui.screens.settings.SobreScreen

@Composable
fun SetupNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.Splash.route
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
            HomeScreen(
                onSettingsClick = { navController.navigate(ScreenRoute.Settings.route) },
                onProfileClick = { },
                )
        }

        composable(route = ScreenRoute.Settings.route){
            SettingsScreen(navController = navController)
        }

        composable(route = ScreenRoute.Idiomas.route){
            IdiomaScreen(navController = navController)
        }

        composable(route = ScreenRoute.Sobre.route) {
            SobreScreen(navController = navController)
        }

        composable(
            route = ScreenRoute.ProgressoSetting.route,
            arguments = listOf(navArgument("conceitoEsg"){
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val conceitoEsg = backStackEntry.arguments?.getString("conceitoEsg")
            ProgressoSettingScreen(conceito = conceitoEsg!!, navController = navController)
        }
    }
}