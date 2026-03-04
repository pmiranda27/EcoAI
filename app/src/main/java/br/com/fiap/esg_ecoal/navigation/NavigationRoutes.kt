package br.com.fiap.esg_ecoal.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.fiap.esg_ecoal.ui.screens.SplashScreen

@Composable
fun SetupNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.Splash.route
    ) {
        // Rota para Splash Screen
        composable(route = ScreenRoute.Splash.route) {
            SplashScreen(
                onNavigateToLogin = {
                    // Quando clicar em entrar, vai para a rota 'login'
                    navController.navigate(ScreenRoute.Login.route)
                },
                onNavigateToSignUp = {
                    // Quando clicar, vai para 'signup'
                    navController.navigate(ScreenRoute.SignUp.route)
                }
            )
        }

        // Rota para tela de Login
        composable(route = ScreenRoute.Login.route) {
            // LoginScreen(navController)
        }

        // Rota para tela de Cadastro
        composable(route = ScreenRoute.SignUp.route) {
            // SignUpScreen(navController)
        }
    }
}

