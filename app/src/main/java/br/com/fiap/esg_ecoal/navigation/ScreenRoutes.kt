package br.com.fiap.esg_ecoal.navigation

/**
 * Define as rotas disponíveis no app como constantes seguras
 */

sealed class ScreenRoute(val route: String) {
    object Splash : ScreenRoute("splash_screen")
    object Login : ScreenRoute("login_screen")
    object SignUp : ScreenRoute ("signup_screen")
}