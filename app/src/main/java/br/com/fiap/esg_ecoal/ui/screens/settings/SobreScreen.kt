package br.com.fiap.esg_ecoal.ui.screens.settings

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.esg_ecoal.R
import br.com.fiap.esg_ecoal.ui.components.AppBarDefaultWithGoBackButton
import br.com.fiap.esg_ecoal.ui.theme.ESGEcoalTheme
import androidx.core.net.toUri

data class DesenvolvedorModel(
    val nome: String,
    val sala: String,
    val github: String,
    val linkedin: String,
    val foto: Int = R.drawable.no_photo
)

@Composable
fun SobreScreen(navController: NavHostController) {
    val objetivoParagrafo = "EcoAl é uma plataforma corporativa desenvolvida no ecossistema educacional da FIAP com o objetivo de auxiliar empresas na gestão de práticas de ESG (Environmental, Social and Governance). A aplicação centraliza o acompanhamento de indicadores socioambientais, metas organizacionais e métricas de sustentabilidade, além de utilizar elementos de gamificação para incentivar o engajamento de colaboradores e promover maior transparência na gestão e no compartilhamento de dados."
    val contextoParagrafo = "O desenvolvimento do EcoAl ocorreu no contexto acadêmico da FIAP, como parte de um projeto educacional voltado à aplicação prática de conhecimentos em tecnologia e desenvolvimento de software. A proposta foi criar uma solução que simulasse um cenário real do mercado corporativo, permitindo a aplicação de conceitos de desenvolvimento, design de interfaces e organização de sistemas enquanto se abordava um tema atual e relevante: a gestão de práticas ESG dentro das organizações."

    val contexto = LocalContext.current

    val desenvolvedores = listOf(
        DesenvolvedorModel("Pedro", "2TDSOA", "https://github.com/pmiranda27", "https://www.linkedin.com/in/pedro-miranda-dev27/"),
        DesenvolvedorModel("Beatriz", "2TDSOA", "https://github.com/lotouux", "https://www.linkedin.com/in/beatriz-camargo-serafini-b8b667349/"),
        DesenvolvedorModel("Leonardo", "2TDSOA", "https://github.com/ruivoomt", "https://www.linkedin.com/in/leonardo-martin-moncao/"),
        DesenvolvedorModel("Andy", "2TDSOA", "https://github.com/saanmendes", "https://www.linkedin.com/in/sandra-mendes-55a5012b2/"),
        DesenvolvedorModel("Lucas", "2TDSOA", "https://github.com/lucas-silveira", "https://www.linkedin.com/in/lucas-silveira/"),
    )

    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = { AppBarDefaultWithGoBackButton("Sobre", navController) }
        ) { paddingValues ->
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                item { CardTextoSobre("Objetivo", paragrafo = objetivoParagrafo) }
                item { CardTextoSobre("Contexto de Desenvolvimento", paragrafo = contextoParagrafo) }
                item {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                    ){
                        Text(
                            text = "Equipe",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(desenvolvedores) { desenvolvedor ->
                                CardIntegranteEquipe(
                                    nome = desenvolvedor.nome,
                                    sala = desenvolvedor.sala,
                                    github = desenvolvedor.github,
                                    linkedin = desenvolvedor.linkedin,
                                    imagem = desenvolvedor.foto,
                                    contexto = contexto
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CardTextoSobre(titulo: String = "", paragrafo: String = "") {
    Card(
        modifier = Modifier
            .fillMaxWidth(fraction = 0.90f)
            .border(
                BorderStroke(
                    3.dp,
                    Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        )
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(12.dp)
        ) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = paragrafo,
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun CardIntegranteEquipe(
    nome: String = "",
    sala: String = "",
    github: String = "",
    linkedin: String = "",
    imagem: Int,
    contexto: Context
) {
    ElevatedCard(
        modifier = Modifier
            .border(
                BorderStroke(
                    3.dp,
                    Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        )
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
//            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(imagem),
                contentDescription = "Foto do desenvolvedor ${nome}",
                modifier = Modifier
                    .size(130.dp)
                    .border(
                        BorderStroke(
                            3.dp,
                            Brush.horizontalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.secondary,
                                    MaterialTheme.colorScheme.tertiary
                                )
                            )
                        ),
                        shape = CircleShape
                    )
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Text(
                text = nome,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = sala,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Image(
                    painter = painterResource(R.drawable.icone_github),
                    contentDescription = "Github do desenvolvedor ${nome}",
                    modifier = Modifier
                        .size(60.dp)
                        .clickable(
                            onClick = {
                                val intent = Intent(
                                    Intent.ACTION_VIEW,
                                    github.toUri()
                                )
                                contexto.startActivity(intent)
                            }
                        )
                )
                Spacer(modifier = Modifier.width(16.dp))
                Image(
                    painter = painterResource(R.drawable.icone_linkedin),
                    contentDescription = "Linkedin do desenvolvedor ${nome}",
                    modifier = Modifier
                        .size(60.dp)
                        .clickable(
                            onClick = {
                                val intent = Intent(
                                    Intent.ACTION_VIEW,
                                    linkedin.toUri()
                                )
                                contexto.startActivity(intent)
                            }
                        )
                )
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun PreviewCardTextSobre() {
    ESGEcoalTheme {
        CardTextoSobre("TITULO", "PARAGRAFO")
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun PreviewCardIntegranteEquipe() {
    ESGEcoalTheme {
        CardIntegranteEquipe(
            nome = "Fulano",
            sala = "Sala 1",
            github = "github.com",
            linkedin = "linkedin.com",
            imagem = R.drawable.no_photo,
            contexto = LocalContext.current
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun PreviewSobreScreen() {
    ESGEcoalTheme {
        SobreScreen(navController = rememberNavController())
    }
}