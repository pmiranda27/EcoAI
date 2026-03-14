package br.com.fiap.esg_ecoal.network

import br.com.fiap.esg_ecoal.data.model.CreateGoalRequest
import br.com.fiap.esg_ecoal.data.model.CreateTaskRequest
import br.com.fiap.esg_ecoal.data.model.DimensionsResponse
import br.com.fiap.esg_ecoal.data.model.GoalResponse
import br.com.fiap.esg_ecoal.data.model.ScoreResponse
import br.com.fiap.esg_ecoal.data.model.SignInRequest
import br.com.fiap.esg_ecoal.data.model.SignInResponse
import br.com.fiap.esg_ecoal.data.model.SignUpRequest
import br.com.fiap.esg_ecoal.data.model.SignUpResponse
import br.com.fiap.esg_ecoal.data.model.TaskResponse
import br.com.fiap.esg_ecoal.data.model.UpdateTaskRequest
import br.com.fiap.esg_ecoal.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("api/auth/sign-in")
    suspend fun signIn(@Body request: SignInRequest): Response<SignInResponse>

    @POST("api/auth/sign-up")
    suspend fun signUp(@Body request: SignUpRequest): Response<SignUpResponse>

    @GET("api/auth/me")
    suspend fun getMe(): Response<UserResponse>

    @GET("api/analytics/dimensions")
    suspend fun getDimensions(@Query("period") period: String? = null): Response<DimensionsResponse>

    @GET("api/analytics/score")
    suspend fun getScore(): Response<ScoreResponse>

    @GET("api/goals")
    suspend fun getGoals(@Query("dimension") dimension: String? = null): Response<List<GoalResponse>>

    @POST("api/goals")
    suspend fun createGoal(@Body request: CreateGoalRequest): Response<GoalResponse>

    @DELETE("api/goals/{id}")
    suspend fun deleteGoal(@Path("id") id: Int): Response<Unit>

    @POST("api/goals/{goalId}/tasks")
    suspend fun createTask(
        @Path("goalId") goalId: Int,
        @Body request: CreateTaskRequest
    ): Response<TaskResponse>

    @PUT("api/goals/{goalId}/tasks/{id}")
    suspend fun updateTask(
        @Path("goalId") goalId: Int,
        @Path("id") id: Int,
        @Body request: UpdateTaskRequest
    ): Response<TaskResponse>

    @DELETE("api/goals/{goalId}/tasks/{id}")
    suspend fun deleteTask(
        @Path("goalId") goalId: Int,
        @Path("id") id: Int
    ): Response<Unit>
}
