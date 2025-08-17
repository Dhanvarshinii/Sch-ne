package com.first.beauty.data.remote

import com.first.beauty.data.model.LoggedInUser
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class RegisterRequest(
    val name: String,
    val username: String,
    val email: String,
    val password: String,
    val country: String,
    val dob: String,
    val gender: String
)

data class RegisterResponse(
    val success: Boolean,
    val message: String
)

data class LoginRequest(val username: String, val password: String)
data class LoginResponse(
    val success: Boolean,
    val message: String,
    val user: LoggedInUser? // nullable in case of failure
)



interface ApiService {
    @POST("/api/register")
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("login")
    suspend fun loginUser(@Body request: LoginRequest): LoginResponse
}
