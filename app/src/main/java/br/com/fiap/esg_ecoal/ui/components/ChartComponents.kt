package br.com.fiap.esg_ecoal.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

// Import dos gráficos
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.charts.BarChart

// Imports dos dados
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

// Para fazer o gráfico agrupado
import com.github.mikephil.charting.components.XAxis


// Import do formatador de texto
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

@Composable
fun RadarChartComponent(
    labels: List<String>,
    values: List<Float>,
    label: String,
    color: Int
) {
    AndroidView(
        factory = { context ->
            RadarChart(context).apply {
                description.isEnabled = false
                animateXY(1000, 1000)
            }
        },
        modifier = Modifier.fillMaxWidth().height(300.dp),
        update = { chart ->
            val entries = values.map { RadarEntry(it) }
            val dataSet = RadarDataSet(entries, label).apply {
                this.color = color
                fillColor = color
                setDrawFilled(true)
                fillAlpha = 180
                setDrawValues(false)
            }
            chart.data = RadarData(dataSet)
            chart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
            chart.invalidate()
        }
    )
}

@Composable
fun DoughnutChartComponent(
    dataMap: Map<String, Float>,
    colors: List<Int>,
    centerText: String = ""
) {
    AndroidView(
        factory = { context ->
            PieChart(context).apply {
                isDrawHoleEnabled = true
                holeRadius = 60f
                description.isEnabled = false
                legend.isEnabled = true
                setDrawEntryLabels(false)
            }
        },
        modifier = Modifier.fillMaxWidth().height(300.dp),
        update = { chart ->
            val entries = dataMap.map { PieEntry(it.value, it.key) }
            val dataSet = PieDataSet(entries, "").apply {
                this.colors = colors
                sliceSpace = 3f
                setDrawValues(false)
            }
            chart.centerText = centerText
            chart.data = PieData(dataSet)
            chart.animateY(1400)
            chart.invalidate()
        }
    )
}

@Composable
fun BarChartComponent(
    labels: List<String>,
    values: List<Float>,
    label: String,
    barColor: Int
) {
    AndroidView(
        factory = { context ->
            BarChart(context).apply {
                description.isEnabled = false
                axisRight.isEnabled = false
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.setDrawGridLines(false)
                xAxis.granularity = 1f
                axisLeft.axisMinimum = 0f
                animateY(1000)
            }
        },
        modifier = Modifier.fillMaxWidth().height(300.dp),
        update = { chart ->
            val entries = values.mapIndexed { index, value ->
                BarEntry(index.toFloat(), value)
            }

            val dataSet = BarDataSet(entries, label).apply {
                color = barColor
                valueTextSize = 12f
            }

            chart.data = BarData(dataSet)
            chart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
            chart.invalidate()
        }
    )
}

@Composable
fun GroupedBarChartComponent(
    labels: List<String>,
    valuesGroup1: List<Float>,
    valuesGroup2: List<Float>,
    label1: String,
    label2: String,
    colors: Pair<Int, Int>
) {
    AndroidView(
        factory = { context ->
            BarChart(context).apply {
                description.isEnabled = false
                axisRight.isEnabled = false
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.setDrawGridLines(false)
                xAxis.granularity = 1f
                xAxis.setCenterAxisLabels(true)
                axisLeft.axisMinimum = 0f
                animateY(1000)
            }
        },
        modifier = Modifier.fillMaxWidth().height(300.dp),
        update = { chart ->
            val entries1 = valuesGroup1.mapIndexed { i, v -> BarEntry(i.toFloat(), v) }
            val entries2 = valuesGroup2.mapIndexed { i, v -> BarEntry(i.toFloat(), v) }

            val set1 = BarDataSet(entries1, label1).apply { color = colors.first }
            val set2 = BarDataSet(entries2, label2).apply { color = colors.second }

            val data = BarData(set1, set2)

            // Configurações de espaçamento para as barras não ficarem coladas
            val barWidth = 0.35f
            val barSpace = 0.05f
            val groupSpace = 0.2f
            data.barWidth = barWidth

            chart.data = data
            chart.xAxis.axisMinimum = 0f
            chart.xAxis.axisMaximum = labels.size.toFloat()
            chart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)

            // Agrupa as barras
            chart.groupBars(0f, groupSpace, barSpace)
            chart.invalidate()
        }
    )
}