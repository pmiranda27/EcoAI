package br.com.fiap.esg_ecoal.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import br.com.fiap.esg_ecoal.R
import br.com.fiap.esg_ecoal.data.model.DashboardData
import br.com.fiap.esg_ecoal.data.model.UiState
import br.com.fiap.esg_ecoal.factory.ViewModelFactory
import br.com.fiap.esg_ecoal.repository.AnalyticsRepository
import br.com.fiap.esg_ecoal.ui.components.AppBarDefaultWithGoBackButton
import br.com.fiap.esg_ecoal.ui.components.BarChartComponent
import br.com.fiap.esg_ecoal.ui.components.DoughnutChartComponent
import br.com.fiap.esg_ecoal.ui.components.GroupedBarChartComponent
import br.com.fiap.esg_ecoal.ui.theme.ESGEcoalTheme

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
        Box(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()) {
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
            text = stringResource(R.string.detalhes),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        // Participação por Departamento (Doughnut Chart)
        ChartCard(title = stringResource(R.string.participacao_por_departamento)) {
            val deptColors = listOf(
                colorScheme.primary.toArgb(),
                colorScheme.secondary.toArgb(),
                colorScheme.tertiary.toArgb(),
                colorScheme.error.toArgb()
            )
            DoughnutChartComponent(
                dataMap = data.departmentParticipation,
                colors = deptColors,
                centerText = stringResource(R.string.departamentos)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Consumo de Energia: Realizado vs Meta (Grouped Bar Chart)
        ChartCard(title = stringResource(R.string.consumo_energia_realizado_vs_meta)) {
            GroupedBarChartComponent(
                labels = data.energyConsumption.labels,
                valuesGroup1 = data.energyConsumption.realized,
                valuesGroup2 = data.energyConsumption.goal,
                label1 = stringResource(R.string.realizado),
                label2 = stringResource(R.string.meta),
                colors = Pair(colorScheme.primary.toArgb(), Color.LightGray.toArgb())
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Minhas Contribuições (Bar Chart)
        ChartCard(title = stringResource(R.string.minhas_contribuicoes_por_categoria)) {
            BarChartComponent(
                labels = data.myContributions.labels,
                values = data.myContributions.values,
                label = stringResource(R.string.pontos),
                barColor = colorScheme.secondary.toArgb()
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Engajamento em Treinamentos (Bar Chart)
        ChartCard(title = stringResource(R.string.horas_treinamento_por_trimestre)) {
            BarChartComponent(
                labels = data.trainingEngagement.labels,
                values = data.trainingEngagement.values,
                label = stringResource(R.string.horas_totais),
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
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onBackground.copy(.15f)),
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