package br.com.fiap.esg_ecoal.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.esg_ecoal.ui.theme.ESGEcoalTheme

// OptIn necessário pois as APIs de TopAppBar do Material 3 ainda são consideradas experimentais
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarDefaultWithGoBackButton(title: String = "", navController: NavHostController) {
    // Componente que centraliza o título automaticamente, seguindo padrões modernos de UI
    CenterAlignedTopAppBar(
        // Configuração de cores da AppBar para um visual "Clean"
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.White, // Fundo branco para se fundir com o fundo da tela
            titleContentColor = Color(0xFF1A1A1A), // Cor do título (quase preto para melhor leitura)
            navigationIconContentColor = Color(0xFF1A1A1A) // Cor do ícone de navegação
        ),
        // Definição do conteúdo do Título
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold, // Peso médio para passar seriedade
                    fontSize = 20.sp // Tamanho equilibrado para não dominar a tela
                )
            )
        },
        // Definição do ícone à esquerda (Navegação)
        navigationIcon = {
            IconButton(
                onClick = { navController.popBackStack() }, // Ação de voltar para a tela anterior
                modifier = Modifier.padding(start = 8.dp) // Pequeno recuo da borda esquerda
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew, // Ícone estilo iOS (mais fino e moderno)
                    contentDescription = "Voltar",
                    modifier = Modifier.size(20.dp) // Tamanho reduzido para um aspecto mais elegante
                )
            }
        },
        // Modificador customizado para desenhar elementos visuais atrás do componente
        modifier = Modifier.drawBehind {
            val strokeWidth = 0.5.dp.toPx() // Define a espessura da linha inferior (bem fina)
            val y = size.height - strokeWidth / 2 // Calcula a posição Y na base da AppBar

            // Desenha uma linha horizontal sutil para separar a AppBar do conteúdo da tela
            drawLine(
                color = Color(0xFFEEEEEE), // Cinza muito claro, quase imperceptível
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = strokeWidth
            )
        }
    )
}

// Configuração da Preview para visualização no modo claro (Light Mode)
@Composable
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true // Mostra o fundo branco no painel de Preview
)
fun PreviewAppBarDefault(){
    ESGEcoalTheme {
        AppBarDefaultWithGoBackButton("Título", rememberNavController())
    }
}