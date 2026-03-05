package br.com.fiap.esg_ecoal.ui.screens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.esg_ecoal.R
import br.com.fiap.esg_ecoal.ui.components.AppBarDefaultWithGoBackButton
import br.com.fiap.esg_ecoal.ui.theme.ESGEcoalTheme
import br.com.fiap.esg_ecoal.ui.theme.poppinsFamily

@Composable
fun IdiomaScreen(navController: NavHostController) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Scaffold(
            topBar = { AppBarDefaultWithGoBackButton("Idiomas", navController) },
            containerColor = Color.LightGray
        ) { paddingValues ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                IdiomaOpcaoItem(
                    titulo = "Português",
                    bandeira = painterResource(R.drawable.brasil_flag),
                    marcado = true
                )
                Spacer(modifier = Modifier.height(12.dp))
                IdiomaOpcaoItem(
                    titulo = "Espanhol",
                    bandeira = painterResource(R.drawable.espanha_flag)
                )
                Spacer(modifier = Modifier.height(12.dp))
                IdiomaOpcaoItem(
                    titulo = "Francês",
                    bandeira = painterResource(R.drawable.franca_flag)
                )
                Spacer(modifier = Modifier.height(12.dp))
                IdiomaOpcaoItem(titulo = "Inglês", bandeira = painterResource(R.drawable.eua_flag))
            }
        }
    }
}

@Composable
fun IdiomaOpcaoItem(titulo: String = "", bandeira: Painter, marcado: Boolean = false) {
    Box(
        modifier = Modifier
            .fillMaxWidth(fraction = 0.95f)
            .height(60.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.horizontalGradient(
                        startX = 0.0f,
                        endX = Float.POSITIVE_INFINITY,
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.55f),
                            MaterialTheme.colorScheme.secondary.copy(alpha = 0.55f)
                        )
                    )
                )
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Image(
                    painter = bandeira,
                    contentDescription = titulo,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .fillMaxHeight(fraction = 0.65f)
                )
                Text(
                    text = titulo,
                    style = TextStyle(
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        lineHeight = 16.sp,
                        letterSpacing = 0.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.65f),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Box(
                    modifier = Modifier
                        .fillMaxHeight(fraction = 0.55f)
                        .aspectRatio(1f)
                        .clip(CircleShape)
                        .border(
                            width = 3.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                        .background(
                            if (marcado) MaterialTheme.colorScheme.primary.copy(0.70f)
                            else Color.Transparent
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (marcado) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .fillMaxSize(fraction = 0.85f)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewIdiomaOpcao() {
    ESGEcoalTheme {
        IdiomaOpcaoItem("Português", painterResource(R.drawable.brasil_flag))
    }
}

@Preview
@Composable
private fun PreviewIdiomaScreen() {
    ESGEcoalTheme {
        IdiomaScreen(rememberNavController())
    }
}