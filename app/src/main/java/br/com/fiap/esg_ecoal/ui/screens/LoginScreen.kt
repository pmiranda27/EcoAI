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
import androidx.compose.ui.unit.sp
import br.com.fiap.esg_ecoal.R
import br.com.fiap.esg_ecoal.ui.components.EsgTextField

/**
 * Componente que representa o conteúdo da gaveta (Bottom Sheet) de Login.
 * @param onLoginSuccess Função chamada ao clicar no botão de entrar.
 * @param onClose Função para fechar a gaveta.
 */
@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, onClose: () -> Unit) {

    // --- ESTADOS DE MEMÓRIA (REACTIVE UI) ---
    // remember: Faz o Compose "lembrar" do valor mesmo que a tela seja redesenhada.
    // mutableStateOf: Cria um estado que, ao ser alterado, avisa o Compose para atualizar o desenho da tela.

    var email by remember { mutableStateOf("") }        // Armazena o texto do campo de e-mail.
    var password by remember { mutableStateOf("") }     // Armazena o texto bruto da senha.
    var passwordVisible by remember { mutableStateOf(false) } // Controla se a senha aparece como texto ou asteriscos.

    val scrollState = rememberScrollState()

    // --- LAYOUT DA TELA ---
    Column(
        modifier = Modifier
            .fillMaxWidth()                   // Ocupa toda a largura disponível na gaveta.
            .padding(24.dp)              // Adiciona um respiro de 24dp nas bordas internas.
            .navigationBarsPadding()          // Evita que o conteúdo fique atrás da barra de navegação do sistema (botões do Android).
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally // Centraliza os elementos filhos horizontalmente.
    ) {
        // Título principal
        Text(
            text = stringResource(R.string.bem_vinde_de_volta),
            style = MaterialTheme.typography.headlineSmall, // Estilo de fonte definido no tema.
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        // Subtítulo descritivo
        Text(
            text = stringResource(R.string.acesse_sua_conta_ecoal),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        // Espaço vertical fixo entre os textos e os campos.
        Spacer(modifier = Modifier.height(32.dp))

        // Rótulo (Label) manual para o E-mail
        Text(
            text = stringResource(R.string.email_corporativo),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth() // Garante que o texto ocupe a largura para o TextAlign funcionar.
        )
        EsgTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = "empresa@email.com",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(16.dp))


        // Rótulo (Label) manual para a Senha
        Text(
            text = stringResource(R.string.senha),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        // Campo de entrada de Senha
        EsgTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = stringResource(R.string.digite_sua_senha),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                // Lógica de ícone dinâmico
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

        // Texto clicável "Esqueceu sua senha?"
        Text(
            text = stringResource(R.string.esqueceu_sua_senha),
            style = MaterialTheme.typography.labelMedium.copy(fontSize = 14.sp),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()                  // Ocupa a largura total.
                .padding(top = 8.dp)             // Pequena margem acima do texto.
                .clickable { /* Futura lógica de recuperação de senha */ }, // Torna o texto clicável.
            textAlign = TextAlign.End            // Alinha o texto à direita (fim).
        )

        // Espaço maior antes do botão final.
        Spacer(modifier = Modifier.height(24.dp))

        // Botão principal de ação: ENTRAR
        Button(
            onClick = onLoginSuccess,            // Executa a função de navegação passada por parâmetro.
            modifier = Modifier
                .fillMaxWidth()                  // Botão largo para facilitar o clique.
                .height(56.dp),                  // Altura padrão confortável para o polegar.
            shape = RoundedCornerShape(16.dp),   // Cantos bem arredondados para um visual moderno.
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary // Cor de fundo do botão (rosa).
            )
        ) {
            // Texto dentro do botão.
            Text(stringResource(R.string.entrar), fontWeight = FontWeight.Black, color = Color.White)
        }
    }
}