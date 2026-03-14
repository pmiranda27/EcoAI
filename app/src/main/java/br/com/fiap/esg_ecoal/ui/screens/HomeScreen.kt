package br.com.fiap.esg_ecoal.ui.screens

import android.content.res.Configuration
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
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fiap.esg_ecoal.LocalTokenRepository
import br.com.fiap.esg_ecoal.ui.theme.ESGEcoalTheme
import kotlin.math.sin
import br.com.fiap.esg_ecoal.R
import br.com.fiap.esg_ecoal.data.model.UiState
import br.com.fiap.esg_ecoal.factory.ViewModelFactory
import br.com.fiap.esg_ecoal.navigation.ScreenRoute
import br.com.fiap.esg_ecoal.repository.AnalyticsRepository
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    onSettingsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    homeViewModel: HomeViewModel? = null
) {
    val viewModel = homeViewModel ?: run {
        val vm: HomeViewModel = viewModel(
            factory = ViewModelFactory { HomeViewModel(AnalyticsRepository()) }
        )
        vm
    }

    var selectedTimeframe by remember { mutableStateOf("Mensal") }
    val uriHandler = LocalUriHandler.current
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { visible = true }

    // Load data when timeframe changes
    LaunchedEffect(selectedTimeframe) {
        viewModel.loadData(selectedTimeframe)
    }

    val dimensionsState by viewModel.dimensions.collectAsState()
    val scoreState by viewModel.score.collectAsState()

    val tokenRepository = LocalTokenRepository.current
    val userName by tokenRepository?.userName?.collectAsState(initial = null)
        ?: remember { mutableStateOf(null) }
    val displayName = userName ?: "User"

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                title = {
                    Text(
                        text = "ECOAI",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onSettingsClick) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = stringResource(R.string.configuracoes),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onProfileClick) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = stringResource(R.string.perfil),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            // 1. INTRODUÇÃO
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(R.string.ola_usuario, displayName), fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
            Text(text = stringResource(R.string.veja_desempenho_esg_empresa), fontSize = 16.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(32.dp))

            // 2. GRÁFICOS + FILTROS
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(R.string.indicadores_criticos), fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Mensal", "Trimestral", "Anual").forEach { timeframe ->
                    FilterChip(
                        selected = selectedTimeframe == timeframe,
                        onClick = { selectedTimeframe = timeframe },
                        label = { Text(timeframe) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = colorScheme.primary,
                            selectedLabelColor = colorScheme.onPrimary
                        )
                    )
                }
            }

            when (val dimState = dimensionsState) {
                is UiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = colorScheme.primary)
                    }
                }
                is UiState.Error -> {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(dimState.message, color = colorScheme.error)
                        Spacer(modifier = Modifier.height(8.dp))
                        TextButton(onClick = { viewModel.loadData(selectedTimeframe) }) {
                            Text(stringResource(R.string.relatorios))
                        }
                    }
                }
                is UiState.Success -> {
                    val dims = dimState.data
                    AnimatedVisibility(visible = visible, enter = fadeIn() + slideInVertically()) {
                        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                            EsgSection(stringResource(R.string.indicadores_ambientais), (dims.environmental * 100).toFloat(), "", true, colorScheme.secondary)
                            EsgSection(stringResource(R.string.indicadores_sociais), (dims.social * 100).toFloat(), "", true, colorScheme.tertiary)
                            EsgSection(stringResource(R.string.governanca_corporativa), (dims.governance * 100).toFloat(), "", true, colorScheme.onPrimaryContainer)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 3. SCORE ESG
            Text(stringResource(R.string.resultado_consolidado), fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(bottom = 12.dp))
            when (val sc = scoreState) {
                is UiState.Loading -> {
                    Box(modifier = Modifier.fillMaxWidth().height(100.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = colorScheme.primary)
                    }
                }
                is UiState.Error -> {
                    Text(sc.message, color = colorScheme.error)
                }
                is UiState.Success -> {
                    val data = sc.data
                    val level = when {
                        data.scoreProgress <= 0.25 -> "Maturidade Nível 1"
                        data.scoreProgress <= 0.50 -> "Maturidade Nível 2"
                        data.scoreProgress <= 0.75 -> "Maturidade Nível 3"
                        else -> "Maturidade Nível 4"
                    }
                    ScoreCard(data.totalScore.toInt(), data.globalScoreGoal.toInt(), level)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 4. AÇÕES RÁPIDAS
            Text(stringResource(R.string.gestao_operacional), fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                QuickActionItem(
                    modifier = Modifier.weight(1f),
                    label = stringResource(R.string.metas_equipe),
                    icon = Icons.Default.Groups,
                    onClick = {
                        navController.navigate(ScreenRoute.Tasks.createRoute("Environmental"))
                    }
                )
                QuickActionItem(Modifier.weight(1f),
                    stringResource(R.string.relatorios), Icons.Default.Description)
            }

            // 5. RADAR DE NOTÍCIAS
            Spacer(modifier = Modifier.height(32.dp))
            Text(stringResource(R.string.radar_noticias_esg), fontWeight = FontWeight.Bold, fontSize = 18.sp)

            NewsCard("Exame", "Nova taxonomia verde: O que muda para as empresas.") { uriHandler.openUri("https://exame.com/esg/") }
            NewsCard("Nações Unidas", "ODS Brasil: Relatório de transparência 2026.") { uriHandler.openUri("https://brasil.un.org/pt-br/sdgs") }
            NewsCard("Valor Econômico", "Governança e valor de mercado em alta.") { uriHandler.openUri("https://valor.globo.com/esg/") }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun EsgSection(title: String, percentage: Float, trend: String, isUp: Boolean, color: Color) {
    val infiniteTransition = rememberInfiniteTransition(label = "wave")
    val waveOffset by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(3000, easing = LinearEasing), RepeatMode.Restart),
        label = "waveOffset"
    )

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
            if (trend.isNotBlank()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (isUp) Icons.Default.TrendingUp else Icons.Default.TrendingDown,
                        contentDescription = null,
                        tint = if (isUp) Color(0xFF2E7D32) else Color(0xFFD32F2F),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(trend, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = if (isUp) Color(0xFF2E7D32) else Color(0xFFD32F2F))
                }
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
                    .background(color.copy(alpha = 0.12f)))
                Canvas(modifier = Modifier.fillMaxSize()) {
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
                    drawPath(path, color = color)
                }
                Text("${percentage.toInt()}%", fontSize = 44.sp, fontWeight = FontWeight.Black, color = Color.White)
            }
        }
    }
}

@Composable
fun ScoreCard(current: Int, target: Int, level: String) {
    val safeTarget = if (target == 0) 1 else target
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = colorScheme.primary)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                Column {
                    Text(stringResource(R.string.score_esg_atual), color = Color.White.copy(0.7f), fontSize = 14.sp)
                    Text("$current", color = Color.White, fontSize = 36.sp, fontWeight = FontWeight.Bold)
                }
                Surface(color = Color.White.copy(0.2f), shape = RoundedCornerShape(8.dp)) {
                    Text(level, color = Color.White, modifier = Modifier.padding(8.dp), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            LinearProgressIndicator(
                progress = current.toFloat() / safeTarget,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(Color.White.copy(0.1f), CircleShape),
                color = Color(0xFF4CAF50),
                trackColor = Color.Transparent
            )
            Text(stringResource(R.string.meta_da_empresa_pontos, target), color = Color.White.copy(0.6f), fontSize = 12.sp, modifier = Modifier.padding(top = 8.dp))
        }
    }
}

@Composable
fun QuickActionItem(
    modifier: Modifier,
    label: String,
    icon: ImageVector,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .height(90.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colorScheme.primary.copy(alpha = 0.60f)),
        border = BorderStroke(1.dp, colorScheme.primary)
    ) {
        Column(modifier = Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {
            Icon(icon, null, tint = colorScheme.onPrimary, modifier = Modifier.size(28.dp))
            Text(label, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = colorScheme.onPrimary, modifier = Modifier.padding(top = 8.dp))
        }
    }
}

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
                Text(source.uppercase(), fontSize = 10.sp, fontWeight = FontWeight.Black, color = colorScheme.primary)
                Text(title, fontSize = 14.sp, fontWeight = FontWeight.Medium, maxLines = 2, overflow = TextOverflow.Ellipsis)
            }
            Icon(Icons.Default.ChevronRight, null, tint = Color.LightGray)
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun HomeScreenPreview() {
    ESGEcoalTheme {
        val fakeNavController = androidx.navigation.compose.rememberNavController()
        HomeScreen(navController = fakeNavController)
    }
}
