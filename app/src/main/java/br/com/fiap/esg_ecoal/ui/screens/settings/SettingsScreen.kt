package br.com.fiap.esg_ecoal.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import br.com.fiap.esg_ecoal.ui.components.AppBarDefaultWithGoBackButton
import br.com.fiap.esg_ecoal.ui.theme.ESGEcoalTheme


@Composable
fun SettingsScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = { AppBarDefaultWithGoBackButton("Configurações") },
            bottomBar = { BottomBarLogOut() }
        ) { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) { }
        }
    }
}

@Composable
fun BottomBarLogOut(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.horizontalGradient(
                    listOf(
                        MaterialTheme.colorScheme.secondary,
                        MaterialTheme.colorScheme.inversePrimary
                    )
                )
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            TextButton(
                onClick = {}
            ) {
                Text(
                    text = "Deslogar",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewBottomBarLogOut() {
    ESGEcoalTheme {
        BottomBarLogOut()
    }
}

@Preview
@Composable
private fun PreviewSettingsScreen() {
    ESGEcoalTheme {
        SettingsScreen()
    }
}