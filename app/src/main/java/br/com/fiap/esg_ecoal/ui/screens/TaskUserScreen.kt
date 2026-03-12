package br.com.fiap.esg_ecoal.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import br.com.fiap.esg_ecoal.ui.theme.ESGEcoalTheme
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import br.com.fiap.esg_ecoal.ui.components.AppBarDefaultWithGoBackButton
import br.com.fiap.esg_ecoal.ui.components.EsgBottomNavigation

@Composable
fun TaskUserScreen(conceito: String, navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val accentColor = when (conceito.lowercase()) {
        "environmental" -> Color(0xFF8DBD80)
        "social" -> Color(0xFFED4C5C)
        "governance" -> Color(0xFFEB9C6E)
        else -> Color(0xFF8DBD80)
    }

    val tituloExibicao = when (conceito.lowercase()) {
        "environmental" -> "Ambiental"
        "social" -> "Social"
        "governance" -> "Governança"
        else -> conceito
    }

    Scaffold(
        topBar = {
            AppBarDefaultWithGoBackButton(title = "Metas Equipe", navController = navController)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
                containerColor = accentColor,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        },
        bottomBar = {
            EsgBottomNavigation(navController = navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = tituloExibicao,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = accentColor,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            when (conceito.lowercase()) {
                "environmental" -> {
                    ExpandableTaskGroup("Ambiente Limpo", accentColor, listOf("Implementar coleta seletiva", "Criar pontos de reciclagem", "Reduzir uso de plástico descartável", "Descartar lixo eletrônico corretamente"))
                    ExpandableTaskGroup("Energia Sustentável", accentColor, listOf("Trocar lâmpadas por LED", "Desligar equipamentos fora do expediente", "Monitorar consumo mensal de energia", "Incentivar uso de iluminação natural"))
                    ExpandableTaskGroup("Gestão de Resíduos", accentColor, listOf("Separar resíduos recicláveis", "Reduzir desperdício de materiais", "Parceria com empresa de reciclagem", "Criar campanha interna de reciclagem"))
                    ExpandableTaskGroup("Economia de Água", accentColor, listOf("Instalar torneiras econômicas", "Monitorar consumo de água", "Corrigir vazamentos rapidamente", "Incentivar uso consciente da água"))
                    ExpandableTaskGroup("Redução de Carbono", accentColor, listOf("Incentivar trabalho remoto", "Promover uso de transporte sustentável", "Reduzir viagens corporativas desnecessárias", "Medir emissão de carbono da empresa"))
                    ExpandableTaskGroup("Materiais Sustentáveis", accentColor, listOf("Priorizar materiais recicláveis", "Reduzir uso de papel", "Utilizar fornecedores sustentáveis", "Adotar embalagens ecológicas"))
                }
                "social" -> {
                    ExpandableTaskGroup("Diversidade e Inclusão", accentColor, listOf("Criar política de diversidade", "Treinamento sobre inclusão", "Processos seletivos inclusivos", "Monitorar diversidade nas equipes"))
                    ExpandableTaskGroup("Bem-estar dos Funcionários", accentColor, listOf("Programa de saúde mental", "Oferecer horário flexível", "Pesquisa de satisfação interna", "Promover atividades de integração"))
                    ExpandableTaskGroup("Desenvolvimento Profissional", accentColor, listOf("Oferecer cursos e treinamentos", "Criar programa de mentoria", "Incentivar capacitação profissional", "Avaliação de desempenho periódica"))
                    ExpandableTaskGroup("Impacto na Comunidade", accentColor, listOf("Criar programa de voluntariado", "Parcerias com ONGs", "Doações para projetos sociais", "Participação em eventos comunitários"))
                    ExpandableTaskGroup("Ambiente de Trabalho Seguro", accentColor, listOf("Treinamento de segurança no trabalho", "Monitorar condições de trabalho", "Disponibilizar equipamentos de segurança", "Criar canal para reportar riscos"))
                    ExpandableTaskGroup("Igualdade de Oportunidades", accentColor, listOf("Garantir igualdade salarial", "Promover crescimento interno", "Avaliar promoções de forma justa", "Criar políticas contra discriminação"))
                }
                "governance" -> {
                    ExpandableTaskGroup("Ética Corporativa", accentColor, listOf("Criar código de ética", "Treinamento de ética empresarial", "Criar canal de denúncias", "Revisar políticas internas"))
                    ExpandableTaskGroup("Transparência Empresarial", accentColor, listOf("Publicar relatório de sustentabilidade", "Divulgar metas ESG", "Compartilhar resultados com stakeholders", "Atualizar políticas de transparência"))
                    ExpandableTaskGroup("Gestão de Riscos", accentColor, listOf("Identificar riscos operacionais", "Criar plano de mitigação de riscos", "Monitorar riscos regularmente", "Revisar processos de segurança"))
                    ExpandableTaskGroup("Proteção de Dados", accentColor, listOf("Criar política de privacidade", "Treinamento de proteção de dados", "Monitorar segurança da informação", "Atualizar sistemas de segurança"))
                    ExpandableTaskGroup("Conformidade Legal", accentColor, listOf("Monitorar leis e regulamentações", "Garantir cumprimento de normas", "Realizar auditorias internas", "Atualizar políticas conforme legislação"))
                    ExpandableTaskGroup("Responsabilidade Corporativa", accentColor, listOf("Definir metas ESG claras", "Monitorar progresso das metas", "Criar comitê de sustentabilidade", "Avaliar impacto das decisões da empresa"))
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun ExpandableTaskGroup(title: String, color: Color, tasks: List<String>) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = color
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    HorizontalDivider(color = Color(0xFFF0F0F0))
                    tasks.forEach { task ->
                        var checked by remember { mutableStateOf(false) }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { checked = !checked }
                                .padding(vertical = 8.dp)
                        ) {
                            Checkbox(
                                checked = checked,
                                onCheckedChange = { checked = it },
                                colors = CheckboxDefaults.colors(checkedColor = color)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = task, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true, name = "Preview Ambiental")
@Composable
fun PreviewAmbiental() {
    val fakeNavController = rememberNavController()
    ESGEcoalTheme {
        TaskUserScreen(conceito = "Environmental", navController = fakeNavController)
    }
}