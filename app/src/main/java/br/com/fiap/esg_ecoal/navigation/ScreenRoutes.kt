package br.com.fiap.esg_ecoal.navigation

/**
 * Define as rotas disponíveis no app de forma segura (Type-safe).
 * Usar 'sealed class' com 'data object' é o padrão profissional para navegação no Compose.
 */
sealed class ScreenRoute(val route: String) {
    data object Splash : ScreenRoute("splash_screen")
    data object Home : ScreenRoute("home_screen") // Para onde o usuário vai após o Login
    data object Settings : ScreenRoute("settings_screen")
    data object Idiomas : ScreenRoute("idiomas_screen")
    data object Sobre : ScreenRoute("sobre_screen")
}