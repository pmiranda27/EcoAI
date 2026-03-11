package br.com.fiap.esg_ecoal.ui.screens.settings

// Imports de animações
import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.animation.core.*

// Imports de componentes básicos de UI
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

// Ícones do Material Design
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

// Componentes do Material 3
import androidx.compose.material3.*

// Gerenciamento de estado do Compose
import androidx.compose.runtime.*

// Ferramentas de layout e posicionamento
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

// Classes relacionadas a cores
import androidx.compose.ui.graphics.Color

// Controle de escala de imagens
import androidx.compose.ui.layout.ContentScale

// Permite abrir links externos
import androidx.compose.ui.platform.LocalUriHandler

// Permite carregar imagens da pasta drawable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

// Configurações de texto
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

// Preview da tela dentro do Android Studio
import androidx.compose.ui.tooling.preview.Preview

// Unidades de medida (dp e sp)
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Navegação entre telas
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

// Import dos recursos do projeto
import br.com.fiap.esg_ecoal.R

// Import da AppBar
import br.com.fiap.esg_ecoal.ui.components.AppBarDefaultWithGoBackButton

// Tema do aplicativo
import br.com.fiap.esg_ecoal.ui.theme.ESGEcoalTheme


// Composable principal da tela Sobre
@Composable
fun SobreScreen(navController: NavHostController) {

    // Estado usado para controlar o scroll vertical da tela
    val scrollState = rememberScrollState()

    // Variável de estado que controla a animação de entrada
    var visible by remember { mutableStateOf(false) }

    // Quando a tela é carregada, a animação ativa
    LaunchedEffect(Unit) { visible = true }

    // Estrutura base da tela com AppBar no topo
    Scaffold(
        topBar = { AppBarDefaultWithGoBackButton(stringResource(R.string.sobre_ecoai), navController) },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->

        // Coluna principal que organiza todos os elementos verticalmente
        Column(
            modifier = Modifier
                .fillMaxSize() // ocupa toda a tela
                .padding(paddingValues) // respeita o espaço da AppBar
                .verticalScroll(scrollState) // permite rolagem
                .padding(horizontal = 24.dp), // espaçamento lateral
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(32.dp))

            // Animação de entrada do ícone superior
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(1000)) + scaleIn(tween(800))
            ) {

                // Caixa circular que contém o ícone de planta
                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .background(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    // Ícone de planta central
                    Icon(
                        imageVector = Icons.Default.Eco,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Seção que explica o objetivo do aplicativo
            SectionLayout(visible = visible, delay = 200, title = stringResource(R.string.nosso_objetivo)) {

                Text(
                    text = stringResource(R.string.objetivo_ecoai_texto),
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    color = Color(0xFF555555),
                    textAlign = TextAlign.Justify
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Seção explicando o contexto acadêmico do projeto
            SectionLayout(visible = visible, delay = 400, title = stringResource(R.string.contexto_desenvolvimento)) {

                Text(
                    text = stringResource(R.string.contexto_desenvolvimento_texto),
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    color = Color(0xFF444444),
                    textAlign = TextAlign.Justify
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Título da seção de criadores do projeto
            Text(
                text = stringResource(R.string.criadores_do_projeto),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start),
                color = MaterialTheme.colorScheme.primary.copy(.75f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Cards com os integrantes do projeto
            CriadorCard(
                nome = "Beatriz Camargo",
                cargo = stringResource(R.string.estudante_de_ads),
                github = "https://github.com/lotouux",
                linkedin = "https://www.linkedin.com/in/beatriz-camargo-serafini/",
                photo = R.drawable.bea
            )

            CriadorCard(
                nome = "Leonardo Martin",
                cargo = stringResource(R.string.estudante_de_ads),
                github = "https://github.com/ruivoomt",
                linkedin = "https://www.linkedin.com/in/leonardo-martin-moncao/",
                photo = R.drawable.leo
            )

            CriadorCard(
                nome = "Sandra Mendes",
                cargo = stringResource(R.string.estudante_de_ads),
                github = "https://github.com/saanmendes",
                linkedin = "https://www.linkedin.com/in/sandra-mendes-55a5012b2/",
                photo = R.drawable.andy
            )

            CriadorCard(
                nome = "Lucas Silveira",
                cargo = stringResource(R.string.estudante_de_ads),
                github = "https://github.com/lucas-silveira",
                linkedin = "https://www.linkedin.com/in/lucas-silveira/",
                photo = R.drawable.lucas
            )

            CriadorCard(
                nome = "Pedro Miranda",
                cargo = stringResource(R.string.estudante_de_ads),
                github = "https://github.com/pmiranda27",
                linkedin = "https://www.linkedin.com/in/pedro-miranda-dev27/",
                photo = R.drawable.miranda
            )

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}


// Componente reutilizável para criar seções com título e conteúdo
@Composable
fun SectionLayout(
    visible: Boolean,
    delay: Int,
    title: String,
    content: @Composable () -> Unit
) {

    // Animação de entrada da seção
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(tween(800, delay)) { it / 2 } + fadeIn(tween(800, delay))
    ) {

        Column(modifier = Modifier.fillMaxWidth()) {

            // Título da seção
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Conteúdo da seção
            content()
        }
    }
}


// Card que representa cada integrante do projeto
@Composable
fun CriadorCard(
    nome: String,
    cargo: String,
    github: String,
    linkedin: String,
    photo: Int
) {

    // Permite abrir links externos
    val uriHandler = LocalUriHandler.current

    // Card visual do integrante
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),

        shape = RoundedCornerShape(16.dp),

        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onBackground.copy(.1f)),

        border = BorderStroke(1.dp, Color(0xFFEEEEEE))
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Foto circular do integrante
            Image(
                painter = painterResource(id = photo),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Nome e cargo do integrante
            Column(modifier = Modifier.weight(1f)) {

                Text(
                    text = nome,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Text(
                    text = cargo,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            // Botões de redes sociais
            Row {

                // Botão GitHub
                IconButton(onClick = { uriHandler.openUri(github) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.github),
                        contentDescription = "GitHub",
                        modifier = Modifier.size(24.dp)
                    )
                }

                // Botão LinkedIn
                IconButton(onClick = { uriHandler.openUri(linkedin) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.linkedin),
                        contentDescription = "LinkedIn",
                        modifier = Modifier.size(24.dp),
                        tint = Color.Unspecified
                    )
                }
            }
        }
    }
}


// Preview da tela dentro do Android Studio
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewSobreScreen() {

    // Aplica o tema do aplicativo no preview
    ESGEcoalTheme {

        // Cria um NavController falso para evitar erro no preview
        SobreScreen(rememberNavController())
    }
}