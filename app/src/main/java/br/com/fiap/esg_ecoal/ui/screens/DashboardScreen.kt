package br.com.fiap.esg_ecoal.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.esg_ecoal.ui.components.*
import br.com.fiap.esg_ecoal.ui.theme.ESGEcoalTheme
import br.com.fiap.esg_ecoal.R
import br.com.fiap.esg_ecoal.data.model.DashboardData
import br.com.fiap.esg_ecoal.data.model.DimensionsResponse
import br.com.fiap.esg_ecoal.data.model.ScoreResponse
import br.com.fiap.esg_ecoal.data.model.UiState
import br.com.fiap.esg_ecoal.factory.ViewModelFactory
import br.com.fiap.esg_ecoal.repository.AnalyticsRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsDashboardScreen(
    navController: NavHostController,
    viewModel: DashboardViewModel = viewModel(
        factory = ViewModelFactory { DashboardViewModel(AnalyticsRepository()) }
    )
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            AppBarDefaultWithGoBackButton(stringResource(R.string.dashboard_detalhado), navController)
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            when (val state = uiState) {
                is UiState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                is UiState.Error -> {
                    Text(state.message, color = Color.Red, modifier = Modifier.align(Alignment.Center))
                }
                is UiState.Success -> {
                    DashboardContent(data = state.data)
                }
            }
        }
    }
}

@Composable
fun DashboardContent(data: DashboardData) {
    val colorScheme = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Detalhes",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        // Participação por Departamento (Doughnut Chart)
        ChartCard(title = "Participação por Departamento") {
            val deptColors = listOf(
                colorScheme.primary.toArgb(),
                colorScheme.secondary.toArgb(),
                colorScheme.tertiary.toArgb(),
                colorScheme.error.toArgb()
            )
            DoughnutChartComponent(
                dataMap = data.departmentParticipation,
                colors = deptColors,
                centerText = "Depts"
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Consumo de Energia: Realizado vs Meta (Grouped Bar Chart)
        ChartCard(title = "Consumo de Energia (Realizado vs Meta)") {
            GroupedBarChartComponent(
                labels = data.energyConsumption.labels,
                valuesGroup1 = data.energyConsumption.realized,
                valuesGroup2 = data.energyConsumption.goal,
                label1 = "Realizado",
                label2 = "Meta",
                colors = Pair(colorScheme.primary.toArgb(), Color.LightGray.toArgb())
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Minhas Contribuições (Bar Chart)
        ChartCard(title = "Minhas Contribuições por Categoria") {
            BarChartComponent(
                labels = data.myContributions.labels,
                values = data.myContributions.values,
                label = "Pontos",
                barColor = colorScheme.secondary.toArgb()
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Engajamento em Treinamentos (Bar Chart)
        ChartCard(title = "Horas de Treinamento por Trimestre") {
            BarChartComponent(
                labels = data.trainingEngagement.labels,
                values = data.trainingEngagement.values,
                label = "Horas Totais",
                barColor = colorScheme.tertiary.toArgb()
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun ChartCard(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp))
            content()
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun DashboardScreenPreview() {
    ESGEcoalTheme {
        DetailsDashboardScreen(rememberNavController())
    }
}