package br.com.fiap.esg_ecoal.ui.screens.settings

import android.content.res.Configuration
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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.esg_ecoal.R
import br.com.fiap.esg_ecoal.data.model.UiState
import br.com.fiap.esg_ecoal.factory.ViewModelFactory
import br.com.fiap.esg_ecoal.repository.GoalsRepository
import br.com.fiap.esg_ecoal.repository.SettingsRepository
import br.com.fiap.esg_ecoal.ui.screens.GoalsViewModel
import br.com.fiap.esg_ecoal.ui.theme.ESGEcoalTheme
import br.com.fiap.esg_ecoal.ui.theme.poppinsFamily
import dataStore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressoSettingScreen(conceito: String, navController: NavHostController) {
    val context = LocalContext.current

    val settingsRepository = remember {
        SettingsRepository(context.dataStore)
    }
    val settingsViewModel: SettingsViewModel = viewModel(
        factory = ViewModelFactory { SettingsViewModel(settingsRepository) }
    )
    val theme by settingsViewModel.theme.collectAsState()

    val goalsViewModel: GoalsViewModel = viewModel(
        factory = ViewModelFactory { GoalsViewModel(GoalsRepository()) }
    )
    val goalsState by goalsViewModel.goals.collectAsState()

    LaunchedEffect(conceito) {
        goalsViewModel.loadGoals(conceito)
    }

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
                        containerColor = Color.Transparent
                    ),
                    title = {
                        Text(
                            text = stringResource(R.string.progresso),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 20.sp
                            )
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { navController.popBackStack() },
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBackIosNew,
                                contentDescription = stringResource(R.string.voltar),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                mostrarDialogApagarProgresso = true
                            },
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
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(corConceito.copy(alpha = 0.20f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = iconePagina,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = corConceito
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = tituloPagina,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(8.dp))

                when (val state = goalsState) {
                    is UiState.Loading -> {
                        Spacer(modifier = Modifier.height(32.dp))
                        CircularProgressIndicator(color = corConceito)
                    }
                    is UiState.Error -> {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(state.message, color = colorScheme.error)
                        TextButton(onClick = { goalsViewModel.loadGoals(conceito) }) {
                            Text("Tentar novamente")
                        }
                    }
                    is UiState.Success -> {
                        val goals = state.data
                        val mainProgress = if (goals.isEmpty()) 0f else {
                            goals.map { goal ->
                                if (goal.tasks.isEmpty()) 0f
                                else goal.tasks.count { it.isCompleted }.toFloat() / goal.tasks.size
                            }.average().toFloat()
                        }

                        BarraProgresso(true, corConceito, mainProgress)

                        Spacer(modifier = Modifier.height(16.dp))

                        Box(modifier = Modifier.fillMaxWidth(0.55f).height(1.dp).background(colorScheme.onBackground))

                        Spacer(modifier = Modifier.height(16.dp))

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(goals) { goal ->
                                val goalProgress = if (goal.tasks.isEmpty()) 0f
                                else goal.tasks.count { it.isCompleted }.toFloat() / goal.tasks.size

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = goal.title,
                                            fontSize = 15.sp,
                                            textAlign = TextAlign.Center,
                                            color = colorScheme.onBackground
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        BarraProgresso(false, corConceito, goalProgress)
                                    }
                                }
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
                    .background(colorScheme.background.copy(alpha = 0.85f))
            ) {
                AlertDialog(
                    onDismissRequest = { mostrarDialogApagarProgresso = false },
                    containerColor = if (theme) Color.Transparent else colorScheme.background,
                    modifier = Modifier
                        .fillMaxWidth(fraction = 1f)
                        .border(1.dp, colorScheme.onBackground, RoundedCornerShape(24.dp)),
                    confirmButton = {
                        TextButton(onClick = {
                            // Delete all goals for this dimension
                            val currentState = goalsState
                            if (currentState is UiState.Success) {
                                currentState.data.forEach { goal ->
                                    goalsViewModel.deleteGoal(goal.id)
                                }
                            }
                            mostrarDialogApagarProgresso = false
                        }) {
                            Text(
                                stringResource(R.string.sim),
                                style = TextStyle(
                                    color = colorScheme.error,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = poppinsFamily,
                                    fontSize = 18.sp
                                )
                            )
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { mostrarDialogApagarProgresso = false }) {
                            Text(
                                stringResource(R.string.cancelar),
                                style = TextStyle(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = colorScheme.onBackground.copy(.75f),
                                    fontFamily = poppinsFamily,
                                    fontSize = 16.sp
                                )
                            )
                        }
                    },
                    title = {
                        Text(
                            stringResource(R.string.apagar_progresso),
                            color = colorScheme.primary,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = poppinsFamily
                        )
                    },
                    text = {
                        Text(
                            stringResource(R.string.certeza_apagar_progresso_conceito, conceito),
                            fontFamily = poppinsFamily,
                            fontSize = 16.sp,
                            color = colorScheme.onBackground
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
                    colorScheme.onBackground
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

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun PreviewProgressoSettingScreen() {
    ESGEcoalTheme {
        ProgressoSettingScreen("Social", rememberNavController())
    }
}
