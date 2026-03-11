package br.com.fiap.esg_ecoal.ui.screens.settings

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.res.stringResource
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
import br.com.fiap.esg_ecoal.ui.components.AppBarDefaultWithGoBackButton
import br.com.fiap.esg_ecoal.ui.theme.ESGEcoalTheme
import br.com.fiap.esg_ecoal.ui.theme.poppinsFamily
import dataStore


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController) {
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

    val imagemUsuario by remember {
        mutableStateOf(null)
    }

    var mostrarDialogDeslogar by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = colorScheme.background.copy(alpha = 1f), // Fundo suave baseado no tema
        topBar = {
            AppBarDefaultWithGoBackButton(stringResource(R.string.configuracoes), navController)
        },
        bottomBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { mostrarDialogDeslogar = true },
                color = MaterialTheme.colorScheme.background,
                shadowElevation = 8.dp
            ) {
                Box(modifier = Modifier.padding(20.dp), contentAlignment = Alignment.Center) {
                    Text(
                        stringResource(R.string.sair_da_conta),
                        color = colorScheme.error,
                        fontWeight = FontWeight.Bold
                    )
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
                color = MaterialTheme.colorScheme.background,
                shadowElevation = 1.dp
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = 32.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(MaterialTheme.colorScheme.background, CircleShape)
                                .clip(CircleShape)
                                .border(2.dp, colorScheme.primary, CircleShape)
                        ) {

                            if (imagemUsuario == null) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = stringResource(R.string.foto),
                                    tint = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier
                                        .size(90.dp)
                                        .align(Alignment.Center)
                                )
                            } else {
                                Image(
                                    painter = painterResource(R.drawable.no_photo),
                                    contentDescription = stringResource(R.string.foto),
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }

                        Surface(
                            color = colorScheme.primary,
                            shape = CircleShape,
                            modifier = Modifier
                                .size(28.dp)
                                .border(2.dp, Color.White, CircleShape)
                        ) {
                            Icon(
                                Icons.Default.Edit,
                                null,
                                tint = colorScheme.onPrimary,
                                modifier = Modifier.padding(6.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "User",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorScheme.primary
                    )
                    Text(
                        "User@empresa.com.br",
                        fontSize = 14.sp,
                        color = colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- CATEGORIAS ---
            Column(
                modifier = Modifier.padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SettingsCategory(
                    stringResource(R.string.conta_e_preferencias),
                    colorScheme.primary
                ) {
                    SettingsOptionItem(
                        Icons.Default.Person,
                        stringResource(R.string.dados_pessoais)
                    ) { }
                    SettingsOptionItem(
                        Icons.Default.Lightbulb,
                        stringResource(R.string.tema_do_aplicativo),
                        if (theme) Icons.Default.DarkMode else Icons.Default.LightMode
                    ) {
                        viewModel.changeTheme(!theme)
                    }
                    SettingsOptionItem(Icons.Default.Language, stringResource(R.string.idioma)) {
                        navController.navigate(ScreenRoute.Idiomas.route)
                    }
                }

                SettingsCategory(stringResource(R.string.progresso_esg), colorScheme.primary) {
                    SettingsOptionItem(
                        Icons.Default.Eco,
                        stringResource(R.string.indicadores_ambientais)
                    ) {
                        navController.navigate(ScreenRoute.ProgressoSetting.createRoute("Environmental"))
                    }
                    SettingsOptionItem(
                        Icons.Default.Groups,
                        stringResource(R.string.indicadores_sociais)
                    ) {
                        navController.navigate(ScreenRoute.ProgressoSetting.createRoute("Social"))
                    }
                    SettingsOptionItem(
                        Icons.Default.AccountBalance,
                        stringResource(R.string.governanca_corporativa)
                    ) {
                        navController.navigate(ScreenRoute.ProgressoSetting.createRoute("Governance"))
                    }
                }

                SettingsCategory(stringResource(R.string.suporte), colorScheme.primary) {
                    SettingsOptionItem(Icons.Default.Info, stringResource(R.string.sobre_ecoai)) {
                        navController.navigate(ScreenRoute.Sobre.route)
                    }
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }

    if (mostrarDialogDeslogar) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.85f))
        ) {
            AlertDialog(
                onDismissRequest = { mostrarDialogDeslogar = false },
                title = {
                    Text(
                        stringResource(R.string.desconectar),
                        color = colorScheme.primary,
                        fontSize = 24.sp,
                        fontFamily = poppinsFamily
                    )
                },
                text = {
                    Text(
                        stringResource(R.string.certeza_sair_conta),
                        fontFamily = poppinsFamily,
                        fontSize = 16.sp,
                    )
                },
                confirmButton = {
                    TextButton(onClick = { /* Logout */ }) {
                        Text(
                            stringResource(R.string.sim_sair),
                            color = colorScheme.error,
                            fontWeight = FontWeight.Bold,
                            fontFamily = poppinsFamily,
                            fontSize = 16.sp
                        )
                    }
                },
                dismissButton = {
                    TextButton(onClick = { mostrarDialogDeslogar = false }) {
                        Text(
                            stringResource(R.string.cancelar),
                            color = colorScheme.onSurfaceVariant,
                            fontFamily = poppinsFamily,
                            fontSize = 14.sp
                        )
                    }
                },
                modifier = Modifier
                    .border(1.dp, Color.White, RoundedCornerShape(24.dp)),
                containerColor = Color.Transparent,
                shape = RoundedCornerShape(24.dp)
            )
        }
    }
}

@Composable
fun SettingsCategory(
    titulo: String,
    primaryColor: Color,
    conteudo: @Composable ColumnScope.() -> Unit
) {
    Column {
        Text(
            text = titulo,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = primaryColor.copy(alpha = 0.7f),
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
        )
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
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
    iconeTrailing: ImageVector = Icons.Default.ChevronRight,
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
            Text(
                texto,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Icon(
            iconeTrailing,
            null,
            tint = primaryColor.copy(alpha = 0.3f),
            modifier = Modifier.size(20.dp)
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewSettingsScreen() {
    ESGEcoalTheme {
        SettingsScreen(navController = rememberNavController())
    }
}