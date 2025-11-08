package com.first.beauty.data.repository

import android.content.Context
import com.first.beauty.data.repository.Result

class LoginDataSource(private val context: Context) {

    suspend fun login(username: String, password: String): Result<Boolean> {
        return if (username == "admin" && password == "password") {
            Result.Success(true)
        } else {
            Result.Error("Invalid username or password")
        }
    }

    // New function to check if email is registered
    suspend fun isEmailRegistered(email: String): Boolean {
        // For demonstration, you can replace this with API/DB check
        val registeredEmails = listOf(
            "admin@example.com",
            "user1@example.com",
            "user2@example.com"
        )
        return registeredEmails.contains(email)
    }
}