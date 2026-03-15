package br.com.fiap.esg_ecoal.ui.screens

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fiap.esg_ecoal.data.model.TaskResponse
import br.com.fiap.esg_ecoal.data.model.UiState
import br.com.fiap.esg_ecoal.factory.ViewModelFactory
import br.com.fiap.esg_ecoal.repository.GoalsRepository
import br.com.fiap.esg_ecoal.ui.theme.ESGEcoalTheme
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import br.com.fiap.esg_ecoal.ui.components.AppBarDefaultWithGoBackButton
import br.com.fiap.esg_ecoal.ui.components.EsgBottomNavigation
import br.com.fiap.esg_ecoal.R

@Composable
fun TaskUserScreen(conceito: String, navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val goalsViewModel: GoalsViewModel = viewModel(
        factory = ViewModelFactory { GoalsViewModel(GoalsRepository()) }
    )

    val goalsState by goalsViewModel.goals.collectAsState()

    LaunchedEffect(conceito) {
        goalsViewModel.loadGoals(conceito)
    }

    val accentColor = when (conceito.lowercase()) {
        "environmental" -> Color(0xFF8DBD80)
        "social" -> Color(0xFFED4C5C)
        "governance" -> Color(0xFFEB9C6E)
        else -> Color(0xFF8DBD80)
    }

    val tituloExibicao = when (conceito.lowercase()) {
        "environmental" -> stringResource(R.string.ambiental)
        "social" -> stringResource(R.string.social)
        "governance" -> stringResource(R.string.governanca)
        else -> conceito
    }

    val apiDimension = when (conceito.lowercase()) {
        "environmental" -> "environmental"
        "social" -> "social"
        "governance" -> "governance"
        else -> conceito.lowercase()
    }

    var showCreateGoalDialog by remember { mutableStateOf(false) }
    var showCreateTaskDialog by remember { mutableStateOf<Int?>(null) }

    Scaffold(
        topBar = {
            AppBarDefaultWithGoBackButton(
                title = stringResource(R.string.metas_equipe),
                navController = navController
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showCreateGoalDialog = true },
                containerColor = accentColor,
                contentColor = MaterialTheme.colorScheme.background,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        },
        bottomBar = {
            EsgBottomNavigation(navController = navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = tituloExibicao,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = accentColor,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            when (val state = goalsState) {
                is UiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = accentColor)
                    }
                }

                is UiState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(state.message, color = MaterialTheme.colorScheme.error)
                        Spacer(modifier = Modifier.height(8.dp))
                        TextButton(onClick = { goalsViewModel.loadGoals(conceito) }) {
                            Text(stringResource(R.string.tentar_novamente))
                        }
                    }
                }

                is UiState.Success -> {
                    val goals = state.data
                    if (goals.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                stringResource(R.string.nenhuma_meta_encontrada),
                                color = MaterialTheme.colorScheme.onBackground.copy(.85f)
                            )
                        }
                    } else {
                        goals.forEach { goal ->
                            ExpandableTaskGroup(
                                title = goal.title,
                                color = accentColor,
                                tasks = goal.tasks,
                                onTaskToggle = { task ->
                                    goalsViewModel.toggleTask(goal.id, task)
                                },
                                onAddTask = { showCreateTaskDialog = goal.id },
                                onDeleteTask = { task ->
                                    goalsViewModel.deleteTask(goal.id, task.id)
                                },
                                onDeleteTaskGroup = { goalsViewModel.deleteGoal(goal.id) }
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }

    // Create Goal Dialog
    if (showCreateGoalDialog) {
        CreateGoalDialog(
            onDismiss = { showCreateGoalDialog = false },
            onCreate = { title, description ->
                goalsViewModel.createGoal(title, description, apiDimension)
                showCreateGoalDialog = false
            }
        )
    }

    // Create Task Dialog
    showCreateTaskDialog?.let { goalId ->
        CreateTaskDialog(
            onDismiss = { showCreateTaskDialog = null },
            onCreate = { title, description, score ->
                goalsViewModel.createTask(goalId, title, description, score)
                showCreateTaskDialog = null
            }
        )
    }
}

@Composable
fun ExpandableTaskGroup(
    title: String,
    color: Color,
    tasks: List<TaskResponse>,
    onTaskToggle: (TaskResponse) -> Unit,
    onAddTask: () -> Unit = {},
    onDeleteTask: (TaskResponse) -> Unit = {},
    onDeleteTaskGroup: () -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp
                    ),
                    modifier = Modifier.weight(1f)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "${tasks.count { it.isCompleted }}/${tasks.size}",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(.75f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.onBackground.copy(.75f))
                    if (tasks.isEmpty()) {
                        Text(
                            stringResource(R.string.nenhuma_tarefa),
                            color = Color.Gray,
                            modifier = Modifier.padding(vertical = 12.dp),
                            fontSize = 14.sp
                        )
                    } else {
                        tasks.forEach { task ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onTaskToggle(task) }
                                    .padding(vertical = 8.dp)
                            ) {
                                Checkbox(
                                    checked = task.isCompleted,
                                    onCheckedChange = { onTaskToggle(task) },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = color,
                                        uncheckedColor = MaterialTheme.colorScheme.onBackground,
                                        checkmarkColor = MaterialTheme.colorScheme.onBackground
                                    )
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = task.title,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp
                                        )
                                    )
                                    if (task.score > 0) {
                                        Text(
                                            text = "${task.score.toInt()} pts",
                                            fontSize = 14.sp,
                                            color = MaterialTheme.colorScheme.background
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.weight(1f))
                                IconButton(
                                    onClick = { onDeleteTask(task) }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = stringResource(R.string.deletar_tarefa)
                                    )
                                }
                            }
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        TextButton(
                            onClick = onAddTask,
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.background,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                stringResource(R.string.adicionar_tarefa),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.background
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(
                            onClick = onDeleteTaskGroup
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(R.string.deletar_objetivo),
                                tint = MaterialTheme.colorScheme.background
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CreateGoalDialog(
    onDismiss: () -> Unit,
    onCreate: (title: String, description: String?) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nova Meta") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(stringResource(R.string.titulo)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(stringResource(R.string.descricao_opcional)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onCreate(title, description.ifBlank { null }) },
                enabled = title.isNotBlank()
            ) {
                Text(stringResource(R.string.criar))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancelar))
            }
        }
    )
}

@Composable
fun CreateTaskDialog(
    onDismiss: () -> Unit,
    onCreate: (title: String, description: String?, score: Double?) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var score by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.nova_tarefa)) },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(stringResource(R.string.titulo)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(stringResource(R.string.descricao_opcional)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = score,
                    onValueChange = { score = it },
                    label = { Text(stringResource(R.string.pontuacao_opcional)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onCreate(
                        title,
                        description.ifBlank { null },
                        score.toDoubleOrNull()
                    )
                },
                enabled = title.isNotBlank()
            ) {
                Text(stringResource(R.string.criar))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancelar))
            }
        }
    )
}

@Preview(
    showBackground = true,
    name = "Preview Ambiental",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewAmbiental() {
    val fakeNavController = rememberNavController()
    ESGEcoalTheme {
        TaskUserScreen(conceito = "Environmental", navController = fakeNavController)
    }
}
