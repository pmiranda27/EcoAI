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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * Componente que representa o conteúdo da gaveta (Bottom Sheet) de Login.
 * @param onLoginSuccess Função chamada ao clicar no botão de entrar.
 * @param onClose Função para fechar a gaveta.
 */
@Composable
fun LoginSheetContent(onLoginSuccess: () -> Unit, onClose: () -> Unit) {

    // --- ESTADOS DE MEMÓRIA (REACTIVE UI) ---
    // remember: Faz o Compose "lembrar" do valor mesmo que a tela seja redesenhada.
    // mutableStateOf: Cria um estado que, ao ser alterado, avisa o Compose para atualizar o desenho da tela.

    var email by remember { mutableStateOf("") }        // Armazena o texto do campo de e-mail.
    var password by remember { mutableStateOf("") }     // Armazena o texto bruto da senha.
    var passwordVisible by remember { mutableStateOf(false) } // Controla se a senha aparece como texto ou asteriscos.

    val scrollState = rememberScrollState()

    // --- CONFIGURAÇÃO DE ESTILO ---
    // OutlinedTextFieldDefaults.colors: Define o esquema de cores para os campos de entrada (borda, cursor, etc).
    val customTextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = MaterialTheme.colorScheme.inversePrimary, // Cor da borda quando o usuário clica na caixa.
        unfocusedBorderColor = Color.LightGray,                        // Cor da borda quando a caixa não está selecionada.
        cursorColor = MaterialTheme.colorScheme.inversePrimary,        // Cor do palito que pisca ao digitar.
        focusedPlaceholderColor = Color.Gray                           // Cor do texto de exemplo quando focado.
    )

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
            text = "Bem-vindo de volta",
            style = MaterialTheme.typography.headlineSmall, // Estilo de fonte definido no tema.
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        // Subtítulo descritivo
        Text(
            text = "Acesse sua conta EcoAl",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        // Espaço vertical fixo entre os textos e os campos.
        Spacer(modifier = Modifier.height(32.dp))

        // Rótulo (Label) manual para o E-mail
        Text(
            text = "E-mail corporativo",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth() // Garante que o texto ocupe a largura para o TextAlign funcionar.
        )

        // Campo de entrada de E-mail
        OutlinedTextField(
            value = email,                        // O valor exibido vem da variável 'email'.
            onValueChange = { email = it },       // Toda vez que o usuário digita, a variável 'email' recebe o novo texto ('it').
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            placeholder = { Text("empresa@email.com", color = Color.Gray) }, // Texto de dica dentro da caixa.
            modifier = Modifier.fillMaxWidth(),   // Faz a caixa ocupar toda a largura.
            shape = RoundedCornerShape(12.dp),    // Arredonda os cantos da borda da caixa.
            singleLine = true,                    // Impede que o usuário pule linha (tecla Enter).
            colors = customTextFieldColors        // Aplica as cores de foco/desfoco definidas acima.
        )

        // Espaçamento entre o campo de e-mail e o de senha.
        Spacer(modifier = Modifier.height(16.dp))

        // Rótulo (Label) manual para a Senha
        Text(
            text = "Senha",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        // Campo de entrada de Senha
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Digite sua senha", color = Color.Gray) },
            // Transforma o texto: se 'passwordVisible' for true, mostra o texto; se false, oculta.
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            // Abre o teclado otimizado para senhas.
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            colors = customTextFieldColors,
            // Ícone que aparece no final da caixa (lado direito).
            trailingIcon = {
                // Define a cor do ícone: roxo se ativo, cinza transparente se inativo.
                val iconColor = if (passwordVisible) {
                    MaterialTheme.colorScheme.inversePrimary
                } else {
                    Color.Gray.copy(alpha = 0.5f)
                }

                // Botão clicável para o ícone.
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = painterResource(id = br.com.fiap.esg_ecoal.R.drawable.eye),
                        contentDescription = "Mostrar Senha",
                        tint = iconColor // Aplica a cor dinâmica calculada acima.
                    )
                }
            }
        )

        // Texto clicável "Esqueceu sua senha?"
        Text(
            text = "Esqueceu sua senha?",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.inversePrimary,
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
                containerColor = MaterialTheme.colorScheme.inversePrimary // Cor de fundo do botão (Roxo).
            )
        ) {
            // Texto dentro do botão.
            Text("ENTRAR", fontWeight = FontWeight.Black, color = Color.White)
        }
    }
}