package br.com.fiap.esg_ecoal.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.esg_ecoal.ui.theme.ESGEcoalTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarDefaultWithGoBackButton(title: String = "", navController: NavHostController) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .background(Brush.horizontalGradient(
                startX = 0.0f,
                endX = Float.POSITIVE_INFINITY,
                colors = listOf(
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.secondary
                )
            )),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Voltar",
                        Modifier.size(30.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Spacer(modifier = Modifier.width(32.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    )
}

@Composable
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
fun PreviewAppBarDefault(){
    ESGEcoalTheme {
        AppBarDefaultWithGoBackButton("Título", rememberNavController())
    }
}