package br.com.fiap.esg_ecoal.ui.screens.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.esg_ecoal.R
import br.com.fiap.esg_ecoal.factory.SettingsViewModelFactory
import br.com.fiap.esg_ecoal.navigation.ScreenRoute
import br.com.fiap.esg_ecoal.repository.SettingsRepository
import br.com.fiap.esg_ecoal.ui.theme.ESGEcoalTheme
import dataStore


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController) {
    var mostrarDialogDeslogar by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val settingsRepository = remember {
        SettingsRepository(context.dataStore)
    }
    val viewModel: SettingsViewModel = viewModel(
        factory = SettingsViewModelFactory(settingsRepository)
    )
    val theme by viewModel.theme.collectAsState()

    // Pegando as cores direto do seu MaterialTheme (Rosa)
    val colorScheme = MaterialTheme.colorScheme

    Scaffold(
        containerColor = colorScheme.surfaceVariant.copy(alpha = 0.3f), // Fundo suave baseado no tema
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Configurações",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorScheme.primary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar", tint = colorScheme.primary)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth().clickable { mostrarDialogDeslogar = true },
                color = Color.White,
                shadowElevation = 8.dp
            ) {
                Box(modifier = Modifier.padding(20.dp), contentAlignment = Alignment.Center) {
                    Text("Sair da Conta", color = colorScheme.error, fontWeight = FontWeight.Bold)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // --- HEADER DE PERFIL ---
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                shadowElevation = 1.dp
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = 32.dp)
                ) {
                    Box(contentAlignment = Alignment.BottomEnd) {
                        Image(
                            painter = painterResource(R.drawable.no_photo),
                            contentDescription = "Foto",
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .border(2.dp, colorScheme.primary, CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Surface(
                            color = colorScheme.primary,
                            shape = CircleShape,
                            modifier = Modifier.size(28.dp).border(2.dp, Color.White, CircleShape)
                        ) {
                            Icon(Icons.Default.Edit, null, tint = colorScheme.onPrimary, modifier = Modifier.padding(6.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("User", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = colorScheme.primary)
                    Text("User@empresa.com.br", fontSize = 14.sp, color = colorScheme.onSurfaceVariant)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- CATEGORIAS ---
            Column(
                modifier = Modifier.padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SettingsCategory("CONTA E PREFERÊNCIAS", colorScheme.primary) {
                    SettingsOptionItem(Icons.Default.Person, "Dados Pessoais") { }
                    SettingsOptionItem(Icons.Default.Lightbulb, "Tema do Aplicativo") { }
                    SettingsOptionItem(Icons.Default.Language, "Idioma") { }
                }

                SettingsCategory("PROGRESSO — ESG", colorScheme.primary) {
                    SettingsOptionItem(Icons.Default.Eco, "Indicadores Ambientais") {
                        navController.navigate(ScreenRoute.ProgressoSetting.createRoute("Environmental"))
                    }
                    SettingsOptionItem(Icons.Default.Groups, "Indicadores Sociais") {
                        navController.navigate(ScreenRoute.ProgressoSetting.createRoute("Social"))
                    }
                    SettingsOptionItem(Icons.Default.AccountBalance, "Governança Corporativa") {
                        navController.navigate(ScreenRoute.ProgressoSetting.createRoute("Governance"))
                    }
                }

                SettingsCategory("SUPORTE", colorScheme.primary) {
                    SettingsOptionItem(Icons.Default.Info, "Sobre o EcoAI") {
                        navController.navigate(ScreenRoute.Sobre.route)
                    }
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }

    if (mostrarDialogDeslogar) {
        AlertDialog(
            onDismissRequest = { mostrarDialogDeslogar = false },
            title = { Text("Desconectar", color = colorScheme.primary) },
            text = { Text("Tem certeza que deseja sair da sua conta?") },
            confirmButton = {
                TextButton(onClick = { /* Logout */ }) {
                    Text("Sim, sair", color = colorScheme.error, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogDeslogar = false }) {
                    Text("Cancelar", color = colorScheme.onSurfaceVariant)
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(24.dp)
        )
    }
}

@Composable
fun SettingsCategory(titulo: String, primaryColor: Color, conteudo: @Composable ColumnScope.() -> Unit) {
    Column {
        Text(
            text = titulo,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = primaryColor.copy(alpha = 0.7f),
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
        )
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Color(0xFFEEEEEE))
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                conteudo()
            }
        }
    }
}

@Composable
fun SettingsOptionItem(
    icone: ImageVector,
    texto: String,
    onClick: () -> Unit
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
                color = primaryColor.copy(alpha = 0.1f),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.size(36.dp)
            ) {
                Icon(icone, null, tint = primaryColor, modifier = Modifier.padding(8.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(texto, fontSize = 15.sp, fontWeight = FontWeight.Medium, color = Color(0xFF2D2D2D))
        }
        Icon(Icons.Default.ChevronRight, null, tint = primaryColor.copy(alpha = 0.3f), modifier = Modifier.size(20.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSettingsScreen() {
    ESGEcoalTheme {
        SettingsScreen(navController = rememberNavController())
    }
}