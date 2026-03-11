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

/**
 * Componente que representa o conteúdo da gaveta (Bottom Sheet) de Criar conta.
 * @param onSignupSuccess Função disparada ao clicar no botão final de cadastro.
 * @param onClose Função que pode ser usada para fechar a gaveta.
 */
@Composable
fun SignUpScreen(onSignupSuccess: () -> Unit, onClose: () -> Unit) {

    // --- ESTADOS DE MEMÓRIA (REACTIVE UI) ---
    // Variáveis que "lembram" o que o usuário digita e controlam a interface
    var name by remember { mutableStateOf("") }             // Guarda o nome do usuário
    var enterprise by remember { mutableStateOf("") }       // Guarda o nome da empresa
    var email by remember { mutableStateOf("") }            // Guarda o e-mail digitado
    var password by remember { mutableStateOf("") }          // Guarda a senha digitada
    var passwordVisible by remember { mutableStateOf(false) } // Controla se o ícone do olho mostra ou esconde a senha
    var isAccepted by remember { mutableStateOf(false) }     // Controla se o checkbox de termos está marcado

    // Cria o estado de rolagem para permitir que a coluna deslize quando o teclado subir
    val scrollState = rememberScrollState()

    // --- LAYOUT DA TELA ---
    Column(
        modifier = Modifier
            .fillMaxWidth()                   // Ocupa a largura total da gaveta
            .padding(24.dp)                   // Espaço interno para os itens não encostarem na borda
            .navigationBarsPadding()          // Garante que o conteúdo não fique sob os botões do sistema Android
            .verticalScroll(scrollState),     // Ativa a rolagem vertical (essencial para telas com muitos campos)
        horizontalAlignment = Alignment.CenterHorizontally // Alinha todos os elementos no centro horizontal
    ) {
        // Título principal do Cadastro
        Text(
            text = stringResource(R.string.criar_conta),
            style = MaterialTheme.typography.headlineSmall, // Fonte de destaque (título)
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        // Subtítulo descritivo
        Text(
            text = stringResource(R.string.comece_jornada_esg_empresa),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        // Espaço de 32dp entre o cabeçalho e o primeiro campo
        Spacer(modifier = Modifier.height(32.dp))

        // --- CAMPO NOME ---
        Text(
            text = stringResource(R.string.nome_completo),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth() // Texto ocupa a largura para alinhar à esquerda corretamente
        )
        EsgTextField(
            value = name,
            onValueChange = { name = it },
            placeholder = stringResource(R.string.seu_nome)
        )

        Spacer(modifier = Modifier.height(16.dp)) // Espaçamento entre blocos de campos

        // --- CAMPO EMPRESA ---
        Text(
            text = stringResource(R.string.empresa),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )
        EsgTextField(
            value = enterprise,
            onValueChange = { enterprise = it },
            placeholder = stringResource(R.string.cnpj_da_empresa)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- CAMPO E-MAIL ---
        Text(
            text = stringResource(R.string.email_corporativo),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
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

        // --- CAMPO SENHA ---
        Text(
            text = stringResource(R.string.crie_uma_senha),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
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

        // --- TERMOS DE USO ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically // Centraliza o Checkbox com o texto ao lado
        ) {
            Checkbox(
                checked = isAccepted,                    // Valor lido da variável 'isAccepted'
                onCheckedChange = { isAccepted = it },   // Atualiza o estado ao clicar no quadrado
                colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary) // Check rosa
            )

            Text(
                text = stringResource(R.string.concordo_termos_uso_e_politica_privacidade),
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray,
                // Modifier.clickable: Permite que o usuário clique no texto para marcar o checkbox também
                modifier = Modifier.clickable { isAccepted = !isAccepted }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- BOTÃO FINAL DE CADASTRO ---
        Button(
            onClick = onSignupSuccess,            // Executa a função de sucesso vinda da Splash
            enabled = isAccepted,                 // O botão SÓ funciona (fica clicável) se o checkbox estiver marcado
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary, // Rosa se ativado
                disabledContainerColor = Color.LightGray                   // Cinza se desativado
            )
        ) {
            // Texto do botão que muda de cor se estiver desativado para melhor leitura
            Text(
                text = stringResource(R.string.criar_minha_conta),
                fontWeight = FontWeight.Black,
                color = if (isAccepted) Color.White else Color.Gray
            )
        }
    }
}