package br.com.fiap.esg_ecoal.ui.screens.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.esg_ecoal.R
import br.com.fiap.esg_ecoal.navigation.ScreenRoute
import br.com.fiap.esg_ecoal.ui.components.AppBarDefaultWithGoBackButton
import br.com.fiap.esg_ecoal.ui.theme.ESGEcoalTheme
import br.com.fiap.esg_ecoal.ui.theme.poppinsFamily


@Composable
fun SettingsScreen(navController: NavHostController) {
    var temaEscuro by remember {
        mutableStateOf(false)
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = { AppBarDefaultWithGoBackButton("Configurações", navController) },
            bottomBar = { BottomBarLogOut() },
            containerColor = Color.LightGray
        ) { paddingValues ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(paddingValues)
            ) {
                ElevatedCard(
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = Color.White
                    ),
                    shape = RectangleShape,
                    elevation = CardDefaults.elevatedCardElevation(
                        defaultElevation = 6.dp,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                            .fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(R.drawable.no_photo),
                            contentDescription = "Foto do Usuário",
                            modifier = Modifier
                                .size(140.dp)
                                .border(
                                    BorderStroke(
                                        3.dp,
                                        Brush.horizontalGradient(
                                            colors = listOf(
                                                MaterialTheme.colorScheme.secondary,
                                                MaterialTheme.colorScheme.tertiary
                                            )
                                        )
                                    ),
                                    shape = CircleShape
                                )
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Olá, ${"Fulaninho"}!",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Black
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                LazyColumn() {
                    item {
                        SettingsCategory(stringResource(R.string.preferencias))
                        {
                            SettingsOptionItemWithTrailing(
                                icone = Icons.Default.Lightbulb,
                                texto = "Tema",
                                rota = "",
                                navController = navController,
                                changeTheme = {})
                            Spacer(modifier = Modifier.height(8.dp))
                            SettingsOptionItemWithTrailing(
                                icone = Icons.Default.Language, "Idioma",
                                rota = ScreenRoute.Idiomas.route,
                                navController = navController
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    item {
                        SettingsCategory("Progresso — ESG") {
                            SettingsOptionItem(
                                icone = painterResource(R.drawable.folha_verde),
                                texto = "Ambiental",
                                navigateFunction = {
                                    navController.navigate(
                                        ScreenRoute.ProgressoSetting.createRoute(
                                            "Environmental"
                                        )
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            SettingsOptionItem(
                                icone = painterResource(R.drawable.mao_vermelha),
                                texto = "Social",
                                navigateFunction = {
                                    navController.navigate(
                                        ScreenRoute.ProgressoSetting.createRoute(
                                            "Social"
                                        )
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            SettingsOptionItem(
                                icone = painterResource(R.drawable.governanca_laranja),
                                texto = "Governança",
                                navigateFunction = {
                                    navController.navigate(
                                        ScreenRoute.ProgressoSetting.createRoute(
                                            "Governance"
                                        )
                                    )
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    item {
                        SettingsCategory("Sobre")
                        {
                            SettingsOptionItemWithTrailing(
                                icone = Icons.Default.ErrorOutline,
                                texto = "Sobre",
                                rota = ScreenRoute.Sobre.route,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun SettingsCategory(titulo: String = "", conteudo: @Composable () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = titulo,
            modifier = Modifier
                .align(Alignment.Start),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))

        conteudo()
    }
}

@Composable
fun SettingsOptionItem(
    icone: Painter,
    texto: String = "",
    navigateFunction: () -> Unit
) {
    ElevatedCard(
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 80.dp,
        ),
        modifier = Modifier
            .fillMaxWidth(fraction = 0.95f)
            .clip(RoundedCornerShape(10.dp))
            .clickable(
                onClick = { navigateFunction() }
            )
    ) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .height(50.dp)
                .padding(vertical = 4.dp, horizontal = 12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = icone,
                    modifier = Modifier
                        .size(30.dp),
                    contentDescription = texto
                )
                Text(
                    text = texto,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.85f),
                    style = TextStyle(
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        lineHeight = 16.sp,
                        letterSpacing = 0.sp
                    )
                )
            }
        }
    }
}

@Composable
fun SettingsOptionItemWithTrailing(
    icone: ImageVector = Icons.Default.Error,
    texto: String = "",
    changeTheme: (() -> Unit)? = null,
    temaEscuro: Boolean = false,
    rota: String,
    navController: NavHostController
) {
    ElevatedCard(
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 20.dp,
        ),
        modifier = Modifier
            .fillMaxWidth(fraction = 0.95f)
            .clip(RoundedCornerShape(10.dp))
            .clickable(
                onClick = {
                    navController.navigate(rota)
                }
            )
    ) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .height(50.dp)
                .padding(vertical = 4.dp, horizontal = 12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icone,
                    contentDescription = "Ícone da opção ${texto}",
                    modifier = Modifier
                        .fillMaxHeight(),
                    tint = Color.Black,
                )
                Text(
                    text = texto,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.75f),
                    style = TextStyle(
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        lineHeight = 16.sp,
                        letterSpacing = 0.sp
                    )
                )
                if (changeTheme == null) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        contentDescription = "Opção Clicável",
                    )
                } else {
                    Icon(
                        imageVector = if (temaEscuro) Icons.Default.DarkMode else Icons.Default.LightMode,
                        contentDescription = "Botão de Trocar Tema",
                        tint = Color.Black,
                        modifier = Modifier
                            .fillMaxHeight(0.95f)
                    )
                }
            }
        }
    }
}

@Composable
fun BottomBarLogOut() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.horizontalGradient(
                    listOf(
                        MaterialTheme.colorScheme.secondary,
                        MaterialTheme.colorScheme.inversePrimary
                    )
                )
            )
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            TextButton(
                onClick = {}
            ) {
                Text(
                    text = stringResource(R.string.deslogar),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewBottomBarLogOut() {
    ESGEcoalTheme {
        BottomBarLogOut()
    }
}

@Preview
@Composable
private fun PreviewSettingsScreen() {
    ESGEcoalTheme {
        SettingsScreen(navController = rememberNavController())
    }
}