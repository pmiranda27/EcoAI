package br.com.fiap.esg_ecoal.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.fiap.esg_ecoal.R

@Composable
fun SplashBackground(alpha: Float) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Imagem com efeito de Blur
        Image(
            painter = painterResource(id = R.drawable.flowers3),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .alpha(alpha)
                .blur(radius = 2.dp), // Adiciona o desfoque.
            contentScale = ContentScale.Crop
        )

        // Camada de gradiente para contraste
        Box(
            modifier = Modifier
                .fillMaxSize()
                .alpha(alpha)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(0.6f),
                            Color.Transparent,
                            Color.Black
                        ),
                        startY = 500f
                    )
                )
        )
    }
}