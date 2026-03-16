package br.com.fiap.esg_ecoal.data.model

data class DashboardData(
    val departmentParticipation: Map<String, Float>,
    val myContributions: ChartDataSimple,
    val energyConsumption: ChartDataGrouped,
    val trainingEngagement: ChartDataSimple
)

data class ChartDataSimple(
    val labels: List<String>,
    val values: List<Float>
)

data class ChartDataGrouped(
    val labels: List<String>,
    val realized: List<Float>,
    val goal: List<Float>
)