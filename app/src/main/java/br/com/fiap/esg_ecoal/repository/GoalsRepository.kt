package br.com.fiap.esg_ecoal.repository

import br.com.fiap.esg_ecoal.data.model.CreateGoalRequest
import br.com.fiap.esg_ecoal.data.model.CreateTaskRequest
import br.com.fiap.esg_ecoal.data.model.GoalResponse
import br.com.fiap.esg_ecoal.data.model.TaskResponse
import br.com.fiap.esg_ecoal.data.model.UpdateTaskRequest
import br.com.fiap.esg_ecoal.network.RetrofitClient

class GoalsRepository {

    private val api = RetrofitClient.apiService

    suspend fun getGoals(dimension: String? = null): Result<List<GoalResponse>> {
        return safeApiCall { api.getGoals(dimension) }
    }

    suspend fun createGoal(
        title: String,
        description: String?,
        dimension: String
    ): Result<GoalResponse> {
        return safeApiCall { api.createGoal(CreateGoalRequest(title, description, dimension)) }
    }

    suspend fun deleteGoal(id: Int): Result<Unit> {
        return safeApiCallNoBody { api.deleteGoal(id) }
    }

    suspend fun createTask(
        goalId: Int,
        title: String,
        description: String? = null,
        score: Double? = 25.00
    ): Result<TaskResponse> {
        return safeApiCall { api.createTask(goalId, CreateTaskRequest(title, description, score)) }
    }

    suspend fun updateTask(
        goalId: Int,
        taskId: Int,
        title: String,
        description: String? = null,
        score: Double? = null,
        completed: Boolean? = null,
        completedAt: String? = null
    ): Result<TaskResponse> {
        return safeApiCall {
            api.updateTask(
                goalId, taskId,
                UpdateTaskRequest(title, description, score, completed, completedAt)
            )
        }
    }

    suspend fun deleteTask(goalId: Int, taskId: Int): Result<Unit> {
        return safeApiCallNoBody { api.deleteTask(goalId, taskId) }
    }
}
