package com.first.beauty.data

import android.content.Context

class LoginDataSource(private val context: Context) {

    suspend fun login(username: String, password: String): Result<Boolean> {
        return if (username == "admin" && password == "password") {
            Result.Success(true)
        } else {
            Result.Error("Invalid username or password")
        }
    }
}
