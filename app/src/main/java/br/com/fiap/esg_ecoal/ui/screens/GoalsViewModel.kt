package br.com.fiap.esg_ecoal.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.esg_ecoal.data.model.GoalResponse
import br.com.fiap.esg_ecoal.data.model.TaskResponse
import br.com.fiap.esg_ecoal.data.model.UiState
import br.com.fiap.esg_ecoal.repository.GoalsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GoalsViewModel(private val goalsRepository: GoalsRepository) : ViewModel() {

    private val _goals = MutableStateFlow<UiState<List<GoalResponse>>>(UiState.Loading)
    val goals: StateFlow<UiState<List<GoalResponse>>> = _goals

    private var currentDimension: String? = null

    fun loadGoals(dimension: String) {
        val apiDimension = when (dimension.lowercase()) {
            "environmental" -> "environmental"
            "social" -> "social"
            "governance" -> "governance"
            else -> dimension.lowercase()
        }
        currentDimension = apiDimension
        _goals.value = UiState.Loading
        viewModelScope.launch {
            val result = goalsRepository.getGoals(apiDimension)
            _goals.value = result.fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message ?: "Erro ao carregar metas") }
            )
        }
    }

    fun toggleTask(goalId: Int, task: TaskResponse) {
        val currentState = _goals.value
        if (currentState !is UiState.Success) return

        val newCompleted = !task.isCompleted
        // Optimistic update
        val updatedGoals = currentState.data.map { goal ->
            if (goal.id == goalId) {
                goal.copy(tasks = goal.tasks.map { t ->
                    if (t.id == task.id) t.copy(completed = if (newCompleted) 1 else 0) else t
                })
            } else goal
        }
        _goals.value = UiState.Success(updatedGoals)

        viewModelScope.launch {
            val result = goalsRepository.updateTask(
                goalId = goalId,
                taskId = task.id,
                title = task.title,
                description = task.description,
                score = task.score,
                completed = newCompleted,
                completedAt = if (newCompleted) "now" else null
            )
            result.onFailure {
                // Rollback on failure
                _goals.value = currentState
            }
        }
    }

    fun createGoal(title: String, description: String?, dimension: String) {
        viewModelScope.launch {
            val result = goalsRepository.createGoal(title, description, dimension)
            result.onSuccess {
                currentDimension?.let { loadGoals(it) }
            }
        }
    }

    fun deleteGoal(id: Int) {
        viewModelScope.launch {
            val result = goalsRepository.deleteGoal(id)
            result.onSuccess {
                currentDimension?.let { loadGoals(it) }
            }
        }
    }

    fun createTask(goalId: Int, title: String, description: String?, score: Double?) {
        viewModelScope.launch {
            val result = goalsRepository.createTask(goalId, title, description, score)
            result.onSuccess {
                currentDimension?.let { loadGoals(it) }
            }
        }
    }
}
