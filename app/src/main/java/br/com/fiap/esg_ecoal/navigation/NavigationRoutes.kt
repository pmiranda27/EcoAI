package br.com.fiap.esg_ecoal.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.fiap.esg_ecoal.ui.screens.DetailsDashboardScreen
import br.com.fiap.esg_ecoal.ui.screens.HomeScreen
import br.com.fiap.esg_ecoal.ui.screens.SplashScreen
import br.com.fiap.esg_ecoal.ui.screens.settings.IdiomaScreen
import br.com.fiap.esg_ecoal.ui.screens.settings.ProgressoSettingScreen
import br.com.fiap.esg_ecoal.ui.screens.settings.SettingsScreen
import br.com.fiap.esg_ecoal.ui.screens.settings.SobreScreen
import br.com.fiap.esg_ecoal.ui.screens.TaskUserScreen

@Composable
fun SetupNavigation(
    navController: NavHostController,
    startDestination: String = ScreenRoute.Splash.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = ScreenRoute.Splash.route) {
            SplashScreen(
                onNavigateToLogin = {
                    navController.navigate(ScreenRoute.Home.route) {
                        popUpTo(ScreenRoute.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToSignUp = {
                    navController.navigate(ScreenRoute.Home.route) {
                        popUpTo(ScreenRoute.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = ScreenRoute.Home.route) {
            HomeScreen(
                navController = navController,
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

        composable(route = ScreenRoute.Dashboard.route){
            DetailsDashboardScreen(navController = navController)
        }

        composable(
            route = ScreenRoute.Tasks.route,
            arguments = listOf(navArgument("conceito") { type = NavType.StringType })
        ) { backStackEntry ->
            val conceito = backStackEntry.arguments?.getString("conceito") ?: "Environmental"
            TaskUserScreen(conceito = conceito, navController = navController)
        }
    }
}
