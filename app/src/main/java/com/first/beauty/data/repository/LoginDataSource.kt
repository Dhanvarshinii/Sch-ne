package com.first.beauty.data.repository

import android.content.Context
import com.first.beauty.data.model.LoggedInUser
import com.first.beauty.data.remote.CheckEmailRequest
import com.first.beauty.data.remote.LoginRequest
import com.first.beauty.data.remote.LoginResponse
import com.first.beauty.data.remote.RetrofitClient.api
import com.first.beauty.data.remote.UserResponse
import com.first.beauty.data.repository.Result

class LoginDataSource(private val context: Context) {

    // ------------------ Normal login ------------------
    suspend fun login(username: String, password: String): Result<LoginResponse> {
        return try {
            val request = LoginRequest(username, password)
            val response = api.loginUser(request)
            if (response.success) {
                Result.Success(response)
            } else {
                Result.Error(response.message ?: "Login failed")
            }
        } catch (e: Exception) {
            Result.Error(e.localizedMessage ?: "Login failed")
        }
    }

    // ------------------ Check if email is already registered (for Google Sign-In) ------------------
    suspend fun isEmailRegistered(email: String): Boolean {
        return try {
            val request = CheckEmailRequest(email)
            val response = api.checkEmail(request)
            response.exists
        } catch (e: Exception) {
            false
        }
    }

    // ------------------ Get user details by email (for Google Sign-In) ------------------
    suspend fun getUserByEmail(email: String): LoggedInUser? {
        return try {
            val request = CheckEmailRequest(email)
            val response: UserResponse = api.getUserByEmail(request)
            if (response.success && response.user != null) {
                response.user
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}
