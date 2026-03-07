package br.com.fiap.esg_ecoal.ui.screens.settings

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.esg_ecoal.ui.components.AppBarDefaultWithGoBackButton
import br.com.fiap.esg_ecoal.ui.theme.ESGEcoalTheme
import br.com.fiap.esg_ecoal.ui.theme.poppinsFamily

data class Meta(
    val titulo: String,
    val progresso: Float
)

@Composable
fun ProgressoSettingScreen(conceito: String, navController: NavHostController) {

    val corConceito = when (conceito.lowercase()) {
        "environmental" -> Color(0xFF8DBD80)
        "social" -> Color(0xFFED4C5C)
        "governance" -> Color(0xFFEB9C6E)
        else -> Color.Gray
    }

    val metasConceito = listOf(
        Meta("Redução de emissão de carbono", 0.75f),
        Meta("Uso de energia renovável", 0f),
        Meta("Diversidade no ambiente de trabalho", 0.5f),
        Meta("Transparência corporativa", 0.25f),
        Meta("Gestão sustentável de resíduos", 1f)
    )

    var mostrarDialogApagarProgresso by remember {
        mutableStateOf(false)
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Scaffold(
            topBar = { AppBarDefaultWithGoBackButton("Progresso", navController) },
            bottomBar = {
                BottomBarRedGradient("Apagar Progresso") {
                    mostrarDialogApagarProgresso = true
                }
            }
        ) { paddingValues ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.85f)
                        .fillMaxHeight(fraction = 0.95f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.secondary.copy(0.65f),
                                    MaterialTheme.colorScheme.primary.copy(0.65f)
                                )
                            )
                        )
                        .border(
                            BorderStroke(
                                3.dp,
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.primary,
                                        MaterialTheme.colorScheme.secondary
                                    )
                                )
                            ),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 24.dp)
                ) {
                    Text(
                        text = conceito,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    BarraProgresso(true, corConceito, 0.5f)
                    Spacer(modifier = Modifier.height(28.dp))
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(metasConceito) { meta ->
                            Text(
                                text = meta.titulo,
                                style = MaterialTheme.typography.displaySmall,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            BarraProgresso(false, corConceito, meta.progresso)
                            Spacer(modifier = Modifier.height(24.dp))
                        }
                    }
                }
            }
        }
        if (mostrarDialogApagarProgresso) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.80f))
            ) {
                AlertDialog(
                    onDismissRequest = { mostrarDialogApagarProgresso = false },
                    containerColor = Color.Transparent,
                    modifier = Modifier
                        .fillMaxWidth(fraction = 1f)
                        .clip(shape = RoundedCornerShape(12.dp))
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.secondary
                                )
                            )
                        ),
                    confirmButton = {
                        Button(
                            onClick = {

                            },
                            contentPadding = PaddingValues(
                                horizontal = 40.dp,
                                vertical = 8.dp
                            ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF53A73D).copy(1f), // verde
                                contentColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(10.dp),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 6.dp
                            )
                        ) {
                            Text(
                                "Sim",
                                style = TextStyle(
                                    fontFamily = poppinsFamily,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White,
                                    fontSize = 22.sp
                                )
                            )
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                mostrarDialogApagarProgresso = false
                            },
                            contentPadding = PaddingValues(horizontal = 40.dp, vertical = 8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFA62B3D).copy(1f), // verde
                                contentColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(10.dp),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 6.dp
                            )
                        ) {
                            Text(
                                "Não",
                                style = TextStyle(
                                    fontFamily = poppinsFamily,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White,
                                    fontSize = 22.sp
                                )
                            )
                        }
                    },
                    title = {
                        Text(
                            "Apagar progresso",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    text = {
                        Text(
                            "Tem certeza que deseja apagar o progresso de ${conceito}?",
                            style = MaterialTheme.typography.displaySmall,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun BarraProgresso(isPrincipal: Boolean, cor: Color, progresso: Float) {
    val corMeio = when (cor) {
        Color(0xFF8DBD80) -> Color(0xFFA5D6A7) // environmental
        Color(0xFFED4C5C) -> Color(0xFFF28B82) // social
        Color(0xFFEB9C6E) -> Color(0xFFF6B48F) // governance
        else -> MaterialTheme.colorScheme.secondary
    }

    val infiniteTransition = rememberInfiniteTransition(label = "progressIdle")

    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "gradientOffset"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth(if (isPrincipal) 0.95f else 0.80f)
            .height(if (isPrincipal) 45.dp else 30.dp)
            .border(
                BorderStroke(
                    2.dp,
                    MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
    ) {

        Box(
            modifier = Modifier
                .align(alignment = Alignment.CenterStart)
                .fillMaxHeight()
                .fillMaxWidth(progresso)
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            cor,
                            corMeio,
                            cor
                        ),
                        start = Offset(offset, 0f),
                        end = Offset(offset + 400f, 0f)
                    )
                )
        )

        Text(
            text = "${(progresso * 100).toInt()}%",
            style = TextStyle(
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = if (isPrincipal) 28.sp else 20.sp
            ),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun BottomBarRedGradient(
    texto: String = "",
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(
                Brush.horizontalGradient(
                    listOf(
                        Color(0xFFED4C5C),
                        Color(0xFFE30A29)
                    )
                )
            )
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = texto,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview
@Composable
private fun PreviewProgressoSettingScreen() {
    ESGEcoalTheme {
        ProgressoSettingScreen("Social", rememberNavController())
    }
}