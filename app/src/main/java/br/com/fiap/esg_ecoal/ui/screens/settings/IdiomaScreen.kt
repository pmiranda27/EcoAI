package br.com.fiap.esg_ecoal.ui.screens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.esg_ecoal.R
import br.com.fiap.esg_ecoal.ui.components.AppBarDefaultWithGoBackButton
import br.com.fiap.esg_ecoal.ui.theme.ESGEcoalTheme

/**
 * Data class para estruturar as opções de idioma (ID, Nome e Recurso da Imagem/Bandeira)
 */
data class LanguageModel(val id: String, val name: String, val flagRes: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IdiomaScreen(navController: NavHostController) {
    // Estado que armazena qual idioma está selecionado (começa com "pt" - Português)
    var selectedId by remember { mutableStateOf("pt") }

    // Lista estática com os idiomas disponíveis no app, referenciando os drawables das bandeiras
    val idiomas = listOf(
        LanguageModel("pt", "Português", R.drawable.brasil_flag),
        LanguageModel("en", "Inglês", R.drawable.eua_flag),
        LanguageModel("es", "Espanhol", R.drawable.espanha_flag),
        LanguageModel("fr", "Francês", R.drawable.franca_flag)
    )

    // Estrutura principal da tela com barra superior e fundo branco
    Scaffold(
        topBar = { AppBarDefaultWithGoBackButton("Idioma", navController) }, // Componente personalizado de AppBar
        containerColor = Color.White // Define a cor de fundo da tela inteira
    ) { paddingValues -> // Recebe os espaçamentos automáticos da Scaffold
        Column(
            modifier = Modifier
                .fillMaxSize() // Ocupa todo o espaço disponível
                .padding(paddingValues) // Aplica o padding vindo da Scaffold
                .padding(horizontal = 24.dp), // Adiciona margem lateral de 24dp
            horizontalAlignment = Alignment.CenterHorizontally // Centraliza os itens na horizontal
        ) {
            Spacer(modifier = Modifier.height(32.dp)) // Espaço entre o topo e o ícone

            // --- ÍCONE CENTRAL COM CÍRCULO SUAVE ---
            Box(
                modifier = Modifier
                    .size(100.dp) // Define o tamanho do círculo
                    .clip(CircleShape) // Corta o fundo em formato circular
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)), // Fundo leve com a cor primária
                contentAlignment = Alignment.Center // Centraliza o ícone dentro do círculo
            ) {
                Icon(
                    imageVector = Icons.Default.Language, // Ícone de globo/idioma
                    contentDescription = null, // Descrição opcional para acessibilidade
                    modifier = Modifier.size(48.dp), // Tamanho do ícone
                    tint = MaterialTheme.colorScheme.primary // Cor do ícone baseada no tema
                )
            }

            Spacer(modifier = Modifier.height(24.dp)) // Espaço entre ícone e título

            // --- TEXTOS DE APOIO ---
            Text(
                text = "Escolha seu idioma",
                fontSize = 22.sp, // Tamanho da fonte do título
                fontWeight = FontWeight.Bold, // Fonte em negrito
                color = Color(0xFF1A1A1A) // Cor de texto quase preta
            )
            Spacer(modifier = Modifier.height(8.dp)) // Espaço entre título e subtítulo
            Text(
                text = "Selecione o idioma de sua preferência",
                fontSize = 15.sp, // Tamanho da fonte do subtítulo
                color = Color.Gray // Cor cinza para dar menos destaque
            )

            Spacer(modifier = Modifier.height(40.dp)) // Espaço antes da lista de idiomas

            // --- LISTA DE IDIOMAS ---
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp), // Espaçamento de 16dp entre cada item da lista
                modifier = Modifier.fillMaxWidth() // A lista ocupa toda a largura disponível
            ) {
                // Itera sobre a lista de idiomas e cria um card para cada um
                items(idiomas) { idioma ->
                    IdiomaCard(
                        idioma = idioma,
                        isSelected = selectedId == idioma.id, // Verifica se este idioma é o selecionado
                        onSelect = { selectedId = idioma.id } // Atualiza o estado ao clicar
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f)) // Empurra o botão para o final da tela

            Button(
                onClick = {
                    // Lógica para salvar o idioma e voltar ou prosseguir
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                val textoBotao = when(selectedId) {
                    "en" -> "Continue in English"
                    "es" -> "Continuar en Español"
                    "fr" -> "Continuer en Français"
                    else -> "Continuar em Português"
                }

                Text(
                    text = textoBotao,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(40.dp)) // Respiro final para não colar na borda do celular
        }
    }
}

/**
 * Componente que representa cada linha/item de idioma na lista
 */
@Composable
fun IdiomaCard(
    idioma: LanguageModel,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    // Variáveis de estilo que mudam se o item estiver selecionado ou não
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.05f) else Color.White
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else Color(0xFFEEEEEE)
    val textColor = if (isSelected) MaterialTheme.colorScheme.primary else Color(0xFF444444)

    Row(
        modifier = Modifier
            .fillMaxWidth() // Item ocupa a largura total
            .height(70.dp) // Altura fixa de cada card
            .clip(RoundedCornerShape(16.dp)) // Bordas arredondadas de 16dp
            .background(backgroundColor) // Cor de fundo definida acima
            .border(
                width = if (isSelected) 2.dp else 1.dp, // Borda mais grossa se selecionado
                color = borderColor, // Cor da borda definida acima
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onSelect() } // Torna o card clicável
            .padding(horizontal = 16.dp), // Espaçamento interno lateral
        verticalAlignment = Alignment.CenterVertically // Centraliza os itens da linha verticalmente
    ) {
        // Exibe a bandeira do idioma
        Image(
            painter = painterResource(idioma.flagRes),
            contentDescription = null,
            contentScale = ContentScale.Crop, // Ajusta a imagem para preencher o círculo
            modifier = Modifier
                .size(32.dp) // Tamanho da bandeira
                .clip(CircleShape) // Corta a bandeira em formato circular
        )

        Spacer(modifier = Modifier.width(16.dp)) // Espaço entre a bandeira e o texto

        // Nome do idioma
        Text(
            text = idioma.name,
            modifier = Modifier.weight(1f), // Faz o texto ocupar o espaço restante da linha
            fontSize = 16.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium, // Negrito se selecionado
            color = textColor
        )

        // Indicador de Seleção (Círculo lateral com ícone de check)
        Box(
            modifier = Modifier
                .size(24.dp) // Tamanho da bolinha indicadora
                .clip(CircleShape)
                .background(
                    if (isSelected) MaterialTheme.colorScheme.primary // Fica colorido se selecionado
                    else Color.Transparent // Fica transparente se não selecionado
                )
                .border(
                    width = 2.dp,
                    color = if (isSelected) MaterialTheme.colorScheme.primary else Color(0xFFDDDDDD),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            // Se estiver selecionado, exibe um ícone de check branco dentro da bolinha
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

/**
 * Função de visualização
 */
@Preview(showBackground = true)
@Composable
private fun PreviewIdiomaScreen() {
    ESGEcoalTheme {
        IdiomaScreen(rememberNavController())
    }
}