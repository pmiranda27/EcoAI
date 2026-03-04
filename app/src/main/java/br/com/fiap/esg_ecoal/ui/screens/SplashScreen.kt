package br.com.fiap.esg_ecoal.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.fiap.esg_ecoal.R
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import kotlinx.coroutines.delay
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import br.com.fiap.esg_ecoal.ui.components.EsgPill
import br.com.fiap.esg_ecoal.ui.components.GlassButton

/**
 * TELA DE ABERTURA DO APP
 * Ela gereencia a animação inicial da logo e a transiçãop ara as opções de Login e cadastro
 * @param onNavigateToLogin é a função para navegar até a tela de login.
 * @param onNavigateToSignUp é a função para navegar até a tela de cadastro
 */
@Composable
fun SplashScreen(onNavigateToLogin: () -> Unit, onNavigateToSignUp: () -> Unit) {

    // Criação de estados para controlar o gatilho das animações sequenciais
    // O remember é utilizado para se lembrar do estado mesmo e guardar na memória para não resetar se a tela precisar ser atualizada
    var showContent by remember { mutableStateOf(false) }   // Inicia como false para logo não se mover
    var logoMoved by remember { mutableStateOf(false) }     // Inicia como false para o conteúdo não aparecer

    // --- DEFINIÇÕES DE ANIMAÇÃO ---

    // Anima o tamanho da logo (diminui quando move para o topo)
    val logoSize by animateDpAsState(
        targetValue = if (logoMoved) 160.dp else 220.dp,    // (tamanho) Se true 160 se false 220
        animationSpec = tween(1000, easing = FastOutSlowInEasing)   // Duração e tipo de animação
    )

    // Anima a posição vertical da logo (sobe do centro para o topo)
    val logoOffset by animateDpAsState(
        targetValue = if (logoMoved) (-280).dp else 0.dp,   // (posição) se true -280 se false 0
        animationSpec = tween(1000, easing = FastOutSlowInEasing)   // Duração e tipo de animação
    )

    // Controla a transparência (fade-in) dos textos, botões e fundo
    val contentAlpha by animateFloatAsState(
        targetValue = if (showContent) 1f else 0f,  // se true 1 (totalmente visisel) se false 0 (totalemente opaco)
        animationSpec = tween(1500)    // duraçõa da animação
    )
    val contentOffset by animateDpAsState(
        targetValue = if (showContent) 0.dp else 20.dp, // se true, fica na posição original, se false, um deslocamento de 20 pixel para baixo
        animationSpec = tween(1500)     // duração da animação
    )

    // Essa ferramenta diz para a animação não se repetir ao sair e voltar para essa tela
    // Unit faz com que isso rode uma única vez
    LaunchedEffect(Unit) {
        delay(800)      // O app espera 0.8 segundos (tempo para o usuario ver a logo centralizada)
        logoMoved = true           // Muda o estado da logo para true
        delay(400)      // Delay para começar o fade-in do fundo
        showContent = true         // Muda o estado do conteudo para true
    }

    // --- ESTRUTURA VISUAL (LAYOUT)---

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {

        // Imagem de fundo com efeito de transparencia animada
        Image(
            painter = painterResource(id = R.drawable.flowers3),    // Coleta imagem da pasta drawable
            contentDescription = null,                              // Deixa sem descrição
            modifier = Modifier.fillMaxSize().alpha(contentAlpha),  // fillMaxSize() Ocupa toda a largura e altura disponível no celular, alpha(contentAlpha) aplica a tarsparencia criada antes
            contentScale = ContentScale.Crop        // Garante que não fiquuem vazios na tela
        )

        // Gradiente para garantir contraste entre a imagem e os textos brancos
        Box(
            modifier = Modifier.fillMaxSize().alpha(contentAlpha)       // fillMaxSize() Ocupa toda a largura e altura disponível no celular, alpha(contentAlpha) aplica a tarsparencia criada antes
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Black.copy(0.5f), Color.Transparent, Color.Black),
                        startY = 500f
                    )
                )
        )

        // Logo: Mantida fora da Column para mover-se independentemente do layout
        Image(
            painter = painterResource(id = R.drawable.logo1),
            contentDescription = "Logo",
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = logoOffset)
                .size(logoSize)
        )

        // Bloco Central: Slogan e Indicadores ESG
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center) // Centraliza a coluna
                .offset(y = (-70).dp + contentOffset) // Ajuste para ficar abaixo da logo movida
                .alpha(contentAlpha)
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Descrição primária
            Text(
                text = "Transforme sua empresa com sustentabilidade",
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Descrição secundária
            Text(
                text = "Gerencie indicadores ambientais, sociais e de governança de forma simples e eficiente.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Pílulas indicativas dos pilares ESG (Componentes Reutilizáveis)
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                EsgPill("Ambiental")
                EsgPill("Social")
                EsgPill("Governança")
            }
        }

        // Bloco Inferior: Ações de entrada e Termos de Uso
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 40.dp)
                .alpha(contentAlpha),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Ação Principal: Login
            Button(
                onClick = onNavigateToLogin,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text("ENTRAR", fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.inversePrimary)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Ação Secundária: Cadastro (Componente Customizado)
            GlassButton(
                text = "Criar Conta",
                onClick = onNavigateToSignUp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Nota de rodapé
            Text(
                text = "Ao continuar, você concorda com nossos Termos de Uso e Política de Privacidade.",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(0.5f),
                textAlign = TextAlign.Center
            )
        }
    }
}


@androidx.compose.ui.tooling.preview.Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview() {
    br.com.fiap.esg_ecoal.ui.theme.ESGEcoalTheme {
        // Passamos funções vazias {} apenas para o Preview não dar erro
        SplashScreen(
            onNavigateToLogin = {},
            onNavigateToSignUp = {}
        )
    }
}