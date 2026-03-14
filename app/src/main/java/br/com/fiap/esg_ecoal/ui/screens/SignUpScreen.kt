package br.com.fiap.esg_ecoal.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.fiap.esg_ecoal.ui.components.EsgTextField
import br.com.fiap.esg_ecoal.R
import br.com.fiap.esg_ecoal.data.model.UiState

@Composable
fun SignUpScreen(
    onSignupSuccess: () -> Unit,
    onClose: () -> Unit,
    viewModel: AuthViewModel? = null
) {
    var name by remember { mutableStateOf("") }
    var cnpj by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isAccepted by remember { mutableStateOf(false) }

    val signUpState by viewModel?.signUpState?.collectAsState() ?: remember { mutableStateOf(null) }
    val isLoading = signUpState is UiState.Loading

    LaunchedEffect(signUpState) {
        if (signUpState is UiState.Success) {
            viewModel?.resetStates()
            onSignupSuccess()
        }
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .navigationBarsPadding()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.criar_conta),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Text(
            text = stringResource(R.string.comece_jornada_esg_empresa),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Nome
        Text(
            text = stringResource(R.string.nome_completo),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )
        EsgTextField(
            value = name,
            onValueChange = { name = it },
            placeholder = stringResource(R.string.seu_nome)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Empresa (CNPJ)
        Text(
            text = stringResource(R.string.empresa),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )
        EsgTextField(
            value = cnpj,
            onValueChange = { cnpj = it },
            placeholder = stringResource(R.string.cnpj_da_empresa)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // E-mail
        Text(
            text = stringResource(R.string.email_corporativo),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )
        EsgTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = "empresa@email.com",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Senha
        Text(
            text = stringResource(R.string.crie_uma_senha),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )
        EsgTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = stringResource(R.string.minimo_8_caracteres),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val iconColor = if (passwordVisible) MaterialTheme.colorScheme.primary else Color.Gray.copy(alpha = 0.5f)
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = painterResource(id = R.drawable.eye),
                        contentDescription = stringResource(R.string.mostrar_senha),
                        tint = iconColor
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Termos de uso
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isAccepted,
                onCheckedChange = { isAccepted = it },
                colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
            )

            Text(
                text = stringResource(R.string.concordo_termos_uso_e_politica_privacidade),
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray,
                modifier = Modifier.clickable { isAccepted = !isAccepted }
            )
        }

        // Error message
        if (signUpState is UiState.Error) {
            Text(
                text = (signUpState as UiState.Error).message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (viewModel != null) {
                    viewModel.signUp(name, email, password, cnpj)
                } else {
                    onSignupSuccess()
                }
            },
            enabled = isAccepted && !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                disabledContainerColor = Color.LightGray
            )
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = stringResource(R.string.criar_minha_conta),
                    fontWeight = FontWeight.Black,
                    color = if (isAccepted) Color.White else Color.Gray
                )
            }
        }
    }
}
