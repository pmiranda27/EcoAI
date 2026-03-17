package br.com.fiap.esg_ecoal.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fiap.esg_ecoal.LocalTokenRepository
import br.com.fiap.esg_ecoal.R
import br.com.fiap.esg_ecoal.factory.ViewModelFactory
import br.com.fiap.esg_ecoal.repository.AuthRepository
import br.com.fiap.esg_ecoal.ui.components.GlassButton
import br.com.fiap.esg_ecoal.ui.components.SplashBackground
import br.com.fiap.esg_ecoal.ui.components.SplashHeroContent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashScreen(onNavigateToLogin: () -> Unit, onNavigateToSignUp: () -> Unit) {

    val tokenRepository = LocalTokenRepository.current
    val authViewModel: AuthViewModel? = if (tokenRepository != null) {
        viewModel(factory = ViewModelFactory { AuthViewModel(AuthRepository(tokenRepository)) })
    } else null

    var showContent by remember { mutableStateOf(false) }
    var logoMoved by remember { mutableStateOf(false) }
    var currentSheet by remember { mutableStateOf(SheetType.NONE) }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    val logoSize by animateDpAsState(
        targetValue = if (logoMoved) 160.dp else 220.dp,
        animationSpec = tween(1000, easing = FastOutSlowInEasing)
    )

    val logoOffset by animateDpAsState(
        targetValue = if (logoMoved) (-280).dp else 0.dp,
        animationSpec = tween(1000, easing = FastOutSlowInEasing)
    )

    val contentAlpha by animateFloatAsState(
        targetValue = if (showContent) 1f else 0f,
        animationSpec = tween(1500)
    )

    val contentOffset by animateDpAsState(
        targetValue = if (showContent) 0.dp else 20.dp,
        animationSpec = tween(1500)
    )

    LaunchedEffect(Unit) {
        delay(800)
        logoMoved = true
        delay(400)
        showContent = true
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)) {

        SplashBackground(alpha = contentAlpha)

        Image(
            painter = painterResource(id = R.drawable.logo1),
            contentDescription = stringResource(R.string.logo),
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = logoOffset)
                .size(logoSize)
        )

        SplashHeroContent(
            alpha = contentAlpha,
            offset = contentOffset,
            modifier = Modifier.align(Alignment.Center)
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 40.dp)
                .alpha(contentAlpha),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { currentSheet = SheetType.LOGIN },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text(stringResource(R.string.entrar), fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
            }

            Spacer(modifier = Modifier.height(12.dp))

            GlassButton(
                text = stringResource(R.string.criar_conta),
                onClick = { currentSheet = SheetType.SIGNUP }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(R.string.voce_concorda_termos_de_uso_politica_privacidade),
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(0.5f),
                textAlign = TextAlign.Center
            )
        }

        if (currentSheet != SheetType.NONE) {
            ModalBottomSheet(
                onDismissRequest = { currentSheet = SheetType.NONE },
                sheetState = sheetState,
                containerColor = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
            ) {
                when (currentSheet) {
                    SheetType.LOGIN -> {
                        LoginScreen(
                            viewModel = authViewModel,
                            onLoginSuccess = {
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
                            viewModel = authViewModel,
                            onSignupSuccess = {
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
