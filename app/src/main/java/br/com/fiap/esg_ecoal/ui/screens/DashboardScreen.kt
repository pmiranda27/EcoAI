package br.com.fiap.esg_ecoal.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.esg_ecoal.ui.components.*
import br.com.fiap.esg_ecoal.ui.theme.ESGEcoalTheme
import br.com.fiap.esg_ecoal.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsDashboardScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            AppBarDefaultWithGoBackButton(stringResource(R.string.dashboard_detalhado), navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(R.string.detalhes_do_desempenho), fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
            Text(text = stringResource(R.string.visao_detalhada_impacto_corporativo_individual), fontSize = 14.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(24.dp))

            // Gráfico de Participação por Departamento
            ChartCard(title = stringResource(R.string.participacao_por_departamento)) {
                val pieColors = listOf(
                    colorScheme.primary.toArgb(),
                    colorScheme.secondary.toArgb(),
                    colorScheme.tertiary.toArgb(),
                    Color(0xFF81C784).toArgb()
                )
                DoughnutChartComponent(
                    dataMap = mapOf(
                        stringResource(R.string.operacoes) to 40f,
                        stringResource(R.string.logistica) to 25f,
                        stringResource(R.string.administrativo) to 20f,
                        stringResource(R.string.comercial) to 15f
                    ),
                    colors = pieColors,
                    centerText = stringResource(R.string.acoes)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Gráfico de Minhas Contribuições
            ChartCard(title = stringResource(R.string.minhas_contribuicoes_esg)) {
                BarChartComponent(
                    labels = listOf("Reciclagem", "Econ. Energia", "Uso de Água", "Ação Social"),
                    values = listOf(45f, 32f, 28f, 15f),
                    label = stringResource(R.string.pontos_acumulados),
                    barColor = colorScheme.secondary.toArgb()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Gráfico de Consumo de Energia
            ChartCard(title = "Consumo de Energia (Real vs Meta)") {
                GroupedBarChartComponent(
                    labels = listOf("Jan", "Fev", "Mar", "Abr"),
                    valuesGroup1 = listOf(115f, 108f, 102f, 95f),
                    valuesGroup2 = listOf(100f, 100f, 100f, 100f),
                    label1 = stringResource(R.string.realizado),
                    label2 = stringResource(R.string.meta),
                    colors = Pair(colorScheme.error.toArgb(), colorScheme.primary.toArgb())
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Gráfico de Horas de Treinamentos ESG
            ChartCard(title = "Engajamento em Treinamentos ESG") {
                BarChartComponent(
                    labels = listOf("Trim 1", "Trim 2", "Trim 3", "Trim 4"),
                    values = listOf(120f, 150f, 200f, 180f),
                    label = stringResource(R.string.horas_totais),
                    barColor = colorScheme.primary.copy(alpha = 0.7f).toArgb()
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
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

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    ESGEcoalTheme {
        DetailsDashboardScreen(rememberNavController())
    }
}