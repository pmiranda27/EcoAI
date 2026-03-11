package br.com.fiap.esg_ecoal.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

sealed class BottomNavItem(val title: String, val label: String, val icon: ImageVector, val route: String) {
    object Ambiental : BottomNavItem("Ambiental", "Ambiental", Icons.Default.Eco, "task_user_screen/Ambiental")
    object Social : BottomNavItem("Social", "Social", Icons.Default.Person, "task_user_screen/Social")
    object Governanca : BottomNavItem("Governanca", "Governança", Icons.Default.AccountBalance, "task_user_screen/Governanca")
}

@Composable
fun EsgBottomNavigation(currentRoute: String?, onNavigate: (String) -> Unit) {
    val items = listOf(
        BottomNavItem.Ambiental,
        BottomNavItem.Social,
        BottomNavItem.Governanca
    )

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        items.forEach { item ->
            val selected = currentRoute?.contains(item.title, ignoreCase = true) == true
            val activeColor = when (item) {
                BottomNavItem.Ambiental -> Color(0xFFD35D6E)
                BottomNavItem.Social -> Color(0xFF8A588B)
                BottomNavItem.Governanca -> Color(0xFF3E5271)
            }

            NavigationBarItem(
                selected = selected,
                onClick = { onNavigate(item.route) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (selected) activeColor else Color.LightGray
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 10.sp,
                        color = if (selected) activeColor else Color.LightGray
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = activeColor.copy(alpha = 0.1f)
                )
            )
        }
    }
}