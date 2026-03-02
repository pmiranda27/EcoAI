package br.com.fiap.esg_ecoal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import br.com.fiap.esg_ecoal.ui.theme.ESGEcoalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ESGEcoalTheme(
                darkTheme = false // por enquanto
            ) {

            }
        }
    }
}