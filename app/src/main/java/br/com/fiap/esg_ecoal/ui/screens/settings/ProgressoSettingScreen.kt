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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.esg_ecoal.R
import br.com.fiap.esg_ecoal.ui.theme.ESGEcoalTheme
import br.com.fiap.esg_ecoal.ui.theme.poppinsFamily

data class Meta(
    val titulo: String,
    val progresso: Float
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressoSettingScreen(conceito: String, navController: NavHostController) {

    val corConceito = when (conceito.lowercase()) {
        "environmental" -> Color(0xFF8DBD80)
        "social" -> Color(0xFFED4C5C)
        "governance" -> Color(0xFFEB9C6E)
        else -> Color.Gray
    }

    val iconePagina = when (conceito.lowercase()) {
        "environmental" -> Icons.Default.Eco
        "social" -> Icons.Default.Groups
        "governance" -> Icons.Default.AccountBalance
        else -> Icons.Default.Error
    }

    val tituloPagina = when (conceito.lowercase()) {
        "environmental" -> stringResource(R.string.indicadores_ambientais)
        "social" -> stringResource(R.string.indicadores_sociais)
        "governance" -> stringResource(R.string.governanca_corporativa)
        else -> "Error"
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
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent // Cor transparente no fundo da barra
                    ),
                    title = {
                        Text(
                            text = stringResource(R.string.progresso), // Título do app
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold, // Peso médio para passar seriedade
                                fontSize = 20.sp // Tamanho equilibrado para não dominar a tela
                            )
                        )
                    },
                    navigationIcon = { // Ícone de configurações no canto esquerdo
                        IconButton(
                            onClick = { navController.popBackStack() }, // Ação do clique no ícone de configurações
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBackIosNew, // Ícone estilo iOS (mais fino e moderno)
                                contentDescription = stringResource(R.string.voltar),
                                modifier = Modifier.size(24.dp) // Tamanho reduzido para um aspecto mais elegante
                            )
                        }
                    },
                    actions = { // Ícone de apagar progresso no canto direito
                        IconButton(
                            onClick = {
                                mostrarDialogApagarProgresso = true
                            }, // Ação do clique no ícone de perfil
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(R.string.apagar_progresso),
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize() // Ocupa todo o espaço disponível
                    .padding(paddingValues) // Aplica o padding vindo da Scaffold
                    .padding(horizontal = 24.dp), // Adiciona margem lateral de 24dp
                horizontalAlignment = Alignment.CenterHorizontally // Centraliza os itens na horizontal
            ) {
                Spacer(modifier = Modifier.height(32.dp)) // Espaço entre o topo e o ícone

                // --- ÍCONE CENTRAL COM CÍRCULO SUAVE ---
                Box(
                    modifier = Modifier
                        .size(100.dp) // Define o tamanho do círculo
                        .clip(CircleShape) // Corta o fundo em formato circular
                        .background(corConceito.copy(alpha = 0.1f)), // Fundo leve com a cor primária
                    contentAlignment = Alignment.Center // Centraliza o ícone dentro do círculo
                ) {
                    Icon(
                        imageVector = iconePagina, // Ícone de globo/idioma
                        contentDescription = null, // Descrição opcional para acessibilidade
                        modifier = Modifier.size(48.dp), // Tamanho do ícone
                        tint = corConceito // Cor do ícone baseada no tema
                    )
                }

                Spacer(modifier = Modifier.height(24.dp)) // Espaço entre ícone e título

                // --- TEXTOS DE APOIO ---
                Text(
                    text = tituloPagina,
                    fontSize = 22.sp, // Tamanho da fonte do título
                    fontWeight = FontWeight.Bold, // Fonte em negrito
                    color = Color(0xFF1A1A1A) // Cor de texto quase preta
                )
                Spacer(modifier = Modifier.height(8.dp)) // Espaço entre título e subtítulo

                BarraProgresso(true, corConceito, 0.5f) // Barra de progresso principal

                Spacer(modifier = Modifier.height(16.dp))

                Box(modifier = Modifier.fillMaxWidth(0.55f).height(1.dp).background(colorScheme.onBackground))

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(metasConceito) { meta ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
//                            shape = RoundedCornerShape(12.dp),
//                            colors = CardDefaults.cardColors(
//                                containerColor = colorScheme.primary.copy(
//                                    alpha = 0.7f
//                                )
//                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = meta.titulo,
                                    fontSize = 15.sp,
                                    textAlign = TextAlign.Center,
                                    color = colorScheme.onBackground
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                BarraProgresso(false, corConceito, meta.progresso)
                            }
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
                                    colorScheme.primary,
                                    colorScheme.secondary
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
                                stringResource(R.string.sim),
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
                                stringResource(R.string.nao),
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
                            stringResource(R.string.apagar_progresso),
                            style = MaterialTheme.typography.titleLarge,
                            color = colorScheme.onPrimary
                        )
                    },
                    text = {
                        Text(
                            stringResource(R.string.certeza_apagar_progresso_conceito, conceito),
                            style = MaterialTheme.typography.displaySmall,
                            color = colorScheme.onPrimary
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
        else -> colorScheme.secondary
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
            .clip(RoundedCornerShape(16.dp))
            .border(
                BorderStroke(
                    2.dp,
                    colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .background(cor.copy(alpha = 0.55f))
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
                fontSize = if (isPrincipal) 28.sp else 16.sp
            ),
            textAlign = TextAlign.Center,
            color = colorScheme.onPrimary
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