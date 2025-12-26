package com.first.beauty.data.repository

import com.first.beauty.data.model.LoggedInUser
import com.first.beauty.data.remote.CheckEmailRequest
import com.first.beauty.data.remote.LoginResponse
import com.first.beauty.data.repository.LoginDataSource
import com.first.beauty.data.repository.Result

class LoginRepository(private val dataSource: LoginDataSource) {

    // ------------------ Normal login ------------------
    suspend fun login(username: String, password: String): Result<LoginResponse> {
        return try {
            dataSource.login(username, password)
        } catch (e: Exception) {
            Result.Error(e.localizedMessage ?: "Login failed")
        }
    }

    // ------------------ Check if email exists ------------------
    suspend fun checkEmailExists(email: String): Boolean {
        return try {
            dataSource.isEmailRegistered(email)
        } catch (e: Exception) {
            false
        }
    }

    // ------------------ Get user details by email (Google login) ------------------
    suspend fun getUserByEmail(email: String): LoggedInUser? {
        return try {
            dataSource.getUserByEmail(email)
        } catch (e: Exception) {
            null
        }
    }
}
