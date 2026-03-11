package br.com.fiap.esg_ecoal.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.esg_ecoal.ui.theme.ESGEcoalTheme
import kotlin.math.sin
import br.com.fiap.esg_ecoal.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onSettingsClick: () -> Unit = {}, onProfileClick: () -> Unit = {}) {
    var selectedTimeframe by remember { mutableStateOf("Mensal") } // Armazena o filtro de tempo selecionado ("Mensal", "Trimestral", "Anual")
    val uriHandler = LocalUriHandler.current // Permite abrir URLs de forma segura
    var visible by remember { mutableStateOf(false) } // Controla a visibilidade da animação

    LaunchedEffect(Unit) { visible = true } // Controla a animação ao montar a tela

    val nomeUsuario by remember {
        mutableStateOf("User")
    }

    Scaffold(
        topBar = { // Definição da barra superior (AppBar)
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent // Cor transparente no fundo da barra
                ),
                title = {
                    Text(
                        text = "ECOAI", // Título do app
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = { // Ícone de configurações no canto esquerdo
                    IconButton(
                        onClick = onSettingsClick, // Ação do clique no ícone de configurações
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = stringResource(R.string.configuracoes),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
                actions = { // Ícone do perfil no canto direito
                    IconButton(
                        onClick = onProfileClick, // Ação do clique no ícone de perfil
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = stringResource(R.string.perfil),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            )
        }
    ) { innerPadding -> // Espaçamento interno da tela, utilizado para evitar sobreposição com a barra
        Column(
            modifier = Modifier
                .padding(innerPadding) // Adiciona espaçamento interno
                .fillMaxSize() // Preenche toda a tela
                .verticalScroll(rememberScrollState()) // Permite rolagem vertical
                .padding(horizontal = 24.dp) // Adiciona padding nas bordas laterais
        ) {
            // 1. INTRODUÇÃO
            Spacer(modifier = Modifier.height(16.dp)) // Espaço entre os elementos
            Text(text = stringResource(R.string.ola_usuario, nomeUsuario), fontSize = 28.sp, fontWeight = FontWeight.ExtraBold) // Saudação
            Text(text = stringResource(R.string.veja_desempenho_esg_empresa), fontSize = 16.sp, color = Color.Gray) // Subtítulo informativo

            Spacer(modifier = Modifier.height(32.dp))

            // 2. GRÁFICOS + FILTROS (DIRETAMENTE LIGADOS)
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(R.string.indicadores_criticos), fontWeight = FontWeight.Bold, fontSize = 18.sp) // Título da seção de indicadores
            }

            // Filtros colados nos gráficos
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Mensal", "Trimestral", "Anual").forEach { timeframe -> // Criação dos filtros de tempo
                    FilterChip(
                        selected = selectedTimeframe == timeframe, // Verifica se o filtro está selecionado
                        onClick = { selectedTimeframe = timeframe }, // Muda o filtro de tempo
                        label = { Text(timeframe) }, // Texto que aparece no filtro
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = colorScheme.primary, // Rosa selecionado
                            selectedLabelColor = colorScheme.onPrimary // Texto contrastante
                        )
                    )
                }
            }

            AnimatedVisibility(visible = visible, enter = fadeIn() + slideInVertically()) { // Animação de entrada
                Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                    EsgSection(stringResource(R.string.indicadores_ambientais), 72f, "+4.2%", true, colorScheme.secondary) // Seção com dados ambientais
                    EsgSection(stringResource(R.string.indicadores_sociais), 58f, "-1.5%", false, colorScheme.tertiary) // Seção com dados sociais
                    EsgSection(stringResource(R.string.governanca_corporativa), 15f, "+10.0%", true, colorScheme.onPrimaryContainer) // Seção com dados de governança
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 3. SCORE ESG (AGORA EMBAIXO DO GRÁFICO)
            Text(stringResource(R.string.resultado_consolidado), fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(bottom = 12.dp)) // Título do resultado consolidado
            ScoreCard(720, 1000, "Maturidade Nível 3") // Exibe o card com o resultado do ESG

            Spacer(modifier = Modifier.height(32.dp))

            // 4. AÇÕES RÁPIDAS
            Text(stringResource(R.string.gestao_operacional), fontWeight = FontWeight.Bold, fontSize = 18.sp) // Título da seção de ações rápidas
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) { // Ações rápidas com ícones
                QuickActionItem(Modifier.weight(1f),
                    stringResource(R.string.metas_equipe), Icons.Default.Groups) // Ação "Metas Equipe"
                QuickActionItem(Modifier.weight(1f),
                    stringResource(R.string.relatorios), Icons.Default.Description) // Ação "Relatórios"
            }

            // 5. RADAR DE NOTÍCIAS
            Spacer(modifier = Modifier.height(32.dp))
            Text(stringResource(R.string.radar_noticias_esg), fontWeight = FontWeight.Bold, fontSize = 18.sp) // Título da seção de notícias ESG

            // Notícias clicáveis
            NewsCard("Exame", "Nova taxonomia verde: O que muda para as empresas.") { uriHandler.openUri("https://exame.com/esg/") }
            NewsCard("Nações Unidas", "ODS Brasil: Relatório de transparência 2026.") { uriHandler.openUri("https://brasil.un.org/pt-br/sdgs") }
            NewsCard("Valor Econômico", "Governança e valor de mercado em alta.") { uriHandler.openUri("https://valor.globo.com/esg/") }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

// Componente para exibir a seção de cada indicador ESG com animação e gráfico
@Composable
fun EsgSection(title: String, percentage: Float, trend: String, isUp: Boolean, color: Color) {
    val infiniteTransition = rememberInfiniteTransition(label = "wave") // Controla a animação de onda
    val waveOffset by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(3000, easing = LinearEasing), RepeatMode.Restart),
        label = "waveOffset"
    )

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Gray) // Título da seção
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (isUp) Icons.Default.TrendingUp else Icons.Default.TrendingDown,
                    contentDescription = null,
                    tint = if (isUp) Color(0xFF2E7D32) else Color(0xFFD32F2F),
                    modifier = Modifier.size(16.dp)
                )
                Text(trend, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = if (isUp) Color(0xFF2E7D32) else Color(0xFFD32F2F)) // Tendência do indicador
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp),
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(1.dp, color.copy(alpha = 0.2f))
        ) {
            Box(contentAlignment = Alignment.Center) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(color.copy(alpha = 0.12f))) // Fundo da seção com a cor customizada
                Canvas(modifier = Modifier.fillMaxSize()) { // Gráfico animado
                    val width = size.width
                    val height = size.height
                    val fillHeight = height * (percentage / 100f)
                    val path = Path().apply {
                        val baseHeight = height - fillHeight
                        moveTo(0f, height)
                        lineTo(0f, baseHeight)
                        for (x in 0..width.toInt() step 5) {
                            val relativeX = x / width
                            val y = baseHeight + sin((relativeX + waveOffset) * 2 * Math.PI).toFloat() * 8.dp.toPx()
                            lineTo(x.toFloat(), y)
                        }
                        lineTo(width, height)
                        close()
                    }
                    drawPath(path, color = color) // Desenha o gráfico animado
                }
                Text("${percentage.toInt()}%", fontSize = 44.sp, fontWeight = FontWeight.Black, color = Color.White) // Exibe a porcentagem
            }
        }
    }
}

// Componente para exibir o card com o score do ESG
@Composable
fun ScoreCard(current: Int, target: Int, level: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = colorScheme.primary) // Cor do card
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                Column {
                    Text(stringResource(R.string.score_esg_atual), color = Color.White.copy(0.7f), fontSize = 14.sp)
                    Text("$current", color = Color.White, fontSize = 36.sp, fontWeight = FontWeight.Bold) // Exibe o score atual
                }
                Surface(color = Color.White.copy(0.2f), shape = RoundedCornerShape(8.dp)) {
                    Text(level, color = Color.White, modifier = Modifier.padding(8.dp), fontSize = 12.sp, fontWeight = FontWeight.Bold) // Nível do score ESG
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            LinearProgressIndicator(
                progress = current.toFloat() / target, // Barra de progresso
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(Color.White.copy(0.1f), CircleShape),
                color = Color(0xFF4CAF50), // Cor da barra
                trackColor = Color.Transparent // Cor do fundo da barra
            )
            Text(stringResource(R.string.meta_da_empresa_pontos, target), color = Color.White.copy(0.6f), fontSize = 12.sp, modifier = Modifier.padding(top = 8.dp))
        }
    }
}

// Componente para exibir um item de ação rápida
@Composable
fun QuickActionItem(modifier: Modifier, label: String, icon: ImageVector) {
    Card(
        modifier = modifier
            .height(90.dp)
            .clickable { },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colorScheme.primary.copy(alpha = 0.60f)),
        border = BorderStroke(1.dp, colorScheme.primary)
    ) {
        Column(modifier = Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {
            Icon(icon, null, tint = colorScheme.onPrimary, modifier = Modifier.size(28.dp)) // Ícone da ação rápida
            Text(label, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = colorScheme.onPrimary, modifier = Modifier.padding(top = 8.dp)) // Texto da ação rápida
        }
    }
}

// Componente para exibir as notícias ESG
@Composable
fun NewsCard(source: String, title: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color.LightGray),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(source.uppercase(), fontSize = 10.sp, fontWeight = FontWeight.Black, color = colorScheme.primary) // Fonte da notícia
                Text(title, fontSize = 14.sp, fontWeight = FontWeight.Medium, maxLines = 2, overflow = TextOverflow.Ellipsis) // Título da notícia
            }
            Icon(Icons.Default.ChevronRight, null, tint = Color.LightGray) // Ícone para indicar mais informações
        }
    }
}

// Preview para visualizar a tela no Android Studio
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ESGEcoalTheme { HomeScreen() } // Exibe a HomeScreen no preview
}