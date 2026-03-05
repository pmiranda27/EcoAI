package br.com.fiap.esg_ecoal.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.fiap.esg_ecoal.R
import br.com.fiap.esg_ecoal.ui.components.GlassButton
import br.com.fiap.esg_ecoal.ui.components.SplashBackground
import br.com.fiap.esg_ecoal.ui.components.SplashHeroContent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * TELA DE ABERTURA (SPLASH)
 * Gerencia a animação da logo e a transição para as opções de entrada.
 * Esta tela agora atua como um "Container", delegando o visual para componentes menores.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashScreen(onNavigateToLogin: () -> Unit, onNavigateToSignUp: () -> Unit) {

    // --- ESTADOS DE CONTROLE ---
    // showContent: Controla quando os botões e textos aparecem (fade-in)
    var showContent by remember { mutableStateOf(false) }
    // logoMoved: Controla quando a logo deve subir para o topo
    var logoMoved by remember { mutableStateOf(false) }

    // currentSheet: Controla qual conteúdo mostrar dentro do ModalBottomSheet.
    // O SheetType é buscado automaticamente do arquivo SplashScreenState.kt
    var currentSheet by remember { mutableStateOf(SheetType.NONE) }

    // --- CONFIGURAÇÕES DO MODAL (GAVETA) ---
    // sheetState: Controla o estado interno da gaveta (aberta/fechada)
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    // scope: Necessário para rodar funções de fechar a gaveta (hide) com animação
    val scope = rememberCoroutineScope()

    // --- DEFINIÇÕES DE ANIMAÇÃO ---

    // Anima o tamanho da logo: diminui de 220dp para 160dp quando ela sobe
    val logoSize by animateDpAsState(
        targetValue = if (logoMoved) 160.dp else 220.dp,
        animationSpec = tween(1000, easing = FastOutSlowInEasing)
    )

    // Anima a posição vertical: sobe 280dp em relação ao centro original
    val logoOffset by animateDpAsState(
        targetValue = if (logoMoved) (-280).dp else 0.dp,
        animationSpec = tween(1000, easing = FastOutSlowInEasing)
    )

    // Anima a transparência (fade): vai de 0 (invisível) a 1 (visível)
    val contentAlpha by animateFloatAsState(
        targetValue = if (showContent) 1f else 0f,
        animationSpec = tween(1500)
    )

    // Anima o leve deslocamento vertical do conteúdo central para um efeito mais fluido
    val contentOffset by animateDpAsState(
        targetValue = if (showContent) 0.dp else 20.dp,
        animationSpec = tween(1500)
    )

    // Gatilho sequencial: Roda apenas uma vez ao entrar na tela para disparar as animações
    LaunchedEffect(Unit) {
        delay(800)          // Espera a logo aparecer no centro
        logoMoved = true    // Faz a logo subir
        delay(400)          // Espera um pouco antes de mostrar o resto
        showContent = true  // Revela o fundo, textos e botões
    }

    // --- ESTRUTURA VISUAL ---
    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {

        // 1. COMPONENTE DE FUNDO: Extraído para profissionalizar o código e permitir reuso
        SplashBackground(alpha = contentAlpha)

        // 2. LOGO ECOAL: Mantida na Screen devido à animação de posição ser muito específica
        Image(
            painter = painterResource(id = R.drawable.logo1),
            contentDescription = "Logo",
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = logoOffset) // Aplica o movimento de subida
                .size(logoSize)         // Aplica a redução de tamanho
        )

        // 3. BLOCO CENTRAL (SLOGAN/ESG): Extraído para reduzir o tamanho deste arquivo
        SplashHeroContent(
            alpha = contentAlpha,
            offset = contentOffset,
            modifier = Modifier.align(Alignment.Center)
        )

        // 4. BLOCO INFERIOR: Botões de Ação (Login e Cadastro)
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 40.dp)
                .alpha(contentAlpha),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Botão "ENTRAR": Altera o estado para abrir a gaveta de LOGIN
            Button(
                onClick = { currentSheet = SheetType.LOGIN },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text("ENTRAR", fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.inversePrimary)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Botão "Criar Conta": Componente customizado que abre a gaveta de SIGNUP
            GlassButton(
                text = "Criar Conta",
                onClick = { currentSheet = SheetType.SIGNUP }
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

        // --- LÓGICA DA GAVETA (MODAL BOTTOM SHEET) ---
        // Exibe a gaveta se currentSheet for LOGIN ou SIGNUP
        if (currentSheet != SheetType.NONE) {
            ModalBottomSheet(
                onDismissRequest = { currentSheet = SheetType.NONE }, // Fecha ao clicar fora ou deslizar para baixo
                sheetState = sheetState,
                containerColor = Color.White,
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
            ) {
                // Alterna o conteúdo da gaveta baseado no estado atual
                when (currentSheet) {
                    SheetType.LOGIN -> {
                        LoginScreen(
                            onLoginSuccess = {
                                // Esconde a gaveta antes de executar a navegação final
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    currentSheet = SheetType.NONE
                                    onNavigateToLogin()
                                }
                            },
                            onClose = {
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    currentSheet = SheetType.NONE
                                }
                            }
                        )
                    }
                    SheetType.SIGNUP -> {
                        SignUpScreen(
                            onSignupSuccess = {
                                // Esconde a gaveta antes de executar a navegação final
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    currentSheet = SheetType.NONE
                                    onNavigateToSignUp()
                                }
                            },
                            onClose = {
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    currentSheet = SheetType.NONE
                                }
                            }
                        )
                    }
                    else -> {}
                }
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview() {
    br.com.fiap.esg_ecoal.ui.theme.ESGEcoalTheme {
        SplashScreen(
            onNavigateToLogin = {},
            onNavigateToSignUp = {}
        )
    }
}