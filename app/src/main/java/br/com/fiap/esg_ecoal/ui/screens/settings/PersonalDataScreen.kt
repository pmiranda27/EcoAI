package br.com.fiap.esg_ecoal.ui.screens.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.AssignmentInd
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.com.fiap.esg_ecoal.LocalTokenRepository
import br.com.fiap.esg_ecoal.R
import br.com.fiap.esg_ecoal.ui.components.AppBarDefaultWithGoBackButton
import br.com.fiap.esg_ecoal.ui.theme.poppinsFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalDataScreen(navController: NavHostController) {
    val colorScheme = MaterialTheme.colorScheme

    val tokenRepository = LocalTokenRepository.current

    val userName by tokenRepository?.userName?.collectAsState(initial = null) ?: remember { mutableStateOf(null) }
    val userEmail by tokenRepository?.userEmail?.collectAsState(initial = null) ?: remember { mutableStateOf(null) }
    val userCnpj by tokenRepository?.userCnpj?.collectAsState(initial = null) ?: remember { mutableStateOf(null) }

    Scaffold(
        containerColor = colorScheme.background,
        topBar = {
            AppBarDefaultWithGoBackButton(stringResource(R.string.dados_pessoais), navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .background(colorScheme.background, CircleShape)
                        .border(2.dp, colorScheme.primary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = colorScheme.primary,
                        modifier = Modifier.size(70.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.suas_informacoes),
                fontFamily = poppinsFamily,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = colorScheme.background),
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(1.dp, colorScheme.onBackground.copy(alpha = 0.1f))
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    InfoItemFuncional(
                        icone = Icons.Default.Person,
                        titulo = stringResource(R.string.nome_usuario),
                        valor = userName ?: stringResource(R.string.carregando)
                    )

                    Divider(Modifier.padding(vertical = 16.dp), thickness = 0.5.dp, color = colorScheme.onBackground.copy(alpha = 0.1f))

                    InfoItemFuncional(
                        icone = Icons.Default.Email,
                        titulo = stringResource(R.string.email_corporativo),
                        valor = userEmail ?: stringResource(R.string.carregando)
                    )

                    Divider(Modifier.padding(vertical = 16.dp), thickness = 0.5.dp, color = colorScheme.onBackground.copy(alpha = 0.1f))

                    InfoItemFuncional(
                        icone = Icons.Default.Business,
                        titulo = stringResource(R.string.cnpj_da_empresa),
                        valor = userCnpj ?: stringResource(R.string.sincronizando)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Surface(
                color = colorScheme.primary.copy(alpha = 0.05f),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(Icons.Default.AssignmentInd, null, tint = colorScheme.primary, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        stringResource(R.string.estes_dados_s_o_sincronizados_com_a_base_da_empresa),
                        fontSize = 12.sp,
                        color = colorScheme.onBackground.copy(alpha = 0.7f),
                        fontFamily = poppinsFamily
                    )
                }
            }
        }
    }
}

@Composable
fun InfoItemFuncional(icone: ImageVector, titulo: String, valor: String) {
    val colorScheme = MaterialTheme.colorScheme
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            color = colorScheme.primary.copy(alpha = 0.1f),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.size(44.dp)
        ) {
            Icon(
                icone,
                contentDescription = null,
                tint = colorScheme.primary,
                modifier = Modifier.padding(10.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = titulo,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
                fontFamily = poppinsFamily
            )
            Text(
                text = valor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorScheme.onBackground,
                fontFamily = poppinsFamily
            )
        }
    }
}