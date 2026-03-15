package br.com.fiap.esg_ecoal.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.esg_ecoal.navigation.ScreenRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.getValue

sealed class BottomNavItem(val pilar: String, val label: String, val icon: ImageVector) {
    object Environmental : BottomNavItem("Environmental", "Ambiental", Icons.Default.Eco)
    object Social : BottomNavItem("Social", "Social", Icons.Default.Person)
    object Governance : BottomNavItem("Governance", "Governança", Icons.Default.AccountBalance)
}

@Composable
fun EsgBottomNavigation(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Environmental,
        BottomNavItem.Social,
        BottomNavItem.Governance
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentArgument = navBackStackEntry?.arguments?.getString("conceito")

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 8.dp
    ) {
        items.forEach { item ->

            val selected = currentArgument?.equals(item.pilar, ignoreCase = true) == true

            val activeColor = when (item) {
                BottomNavItem.Environmental -> Color(0xFF8DBD80)
                BottomNavItem.Social -> Color(0xFFED4C5C)
                BottomNavItem.Governance -> Color(0xFFEB9C6E)
            }

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(ScreenRoute.Tasks.createRoute(item.pilar)) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (selected) activeColor else MaterialTheme.colorScheme.onBackground
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 10.sp,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                        color = if (selected) activeColor else MaterialTheme.colorScheme.onBackground
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = activeColor.copy(alpha = 0.2f)
                )
            )
        }
    }
}