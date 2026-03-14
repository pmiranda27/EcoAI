package br.com.fiap.esg_ecoal.data.model

import com.google.gson.annotations.SerializedName

data class GoalResponse(
    val id: Int,
    val title: String,
    val description: String?,
    val dimension: String,
    @SerializedName("company_id") val companyId: Int,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?,
    val tasks: List<TaskResponse>
)

data class TaskResponse(
    val id: Int,
    val title: String,
    val description: String?,
    val score: Double,
    val completed: Int,
    @SerializedName("completed_at") val completedAt: String?,
    @SerializedName("goal_id") val goalId: Int,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?
) {
    val isCompleted: Boolean get() = completed == 1
}

data class CreateGoalRequest(
    val title: String,
    val description: String?,
    val dimension: String
)

data class CreateTaskRequest(
    val title: String,
    val description: String? = null,
    val score: Double? = null
)

data class UpdateTaskRequest(
    val title: String,
    val description: String? = null,
    val score: Double? = null,
    val completed: Boolean? = null,
    @SerializedName("completed_at") val completedAt: String? = null
)

data class DimensionsResponse(
    val environmental: Double,
    val social: Double,
    val governance: Double
)

data class ScoreResponse(
    @SerializedName("score_progress") val scoreProgress: Double,
    @SerializedName("total_score") val totalScore: Double,
    @SerializedName("global_score_goal") val globalScoreGoal: Double
)
