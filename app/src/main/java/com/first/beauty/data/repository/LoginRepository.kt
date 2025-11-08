package com.first.beauty.data.repository

class LoginRepository(private val dataSource: LoginDataSource) {

    // Login function
    suspend fun login(username: String, password: String): Result<Boolean> {
        return dataSource.login(username, password)
    }

    // New function to check if email is already registered
    suspend fun isEmailRegistered(email: String): Boolean {
        return dataSource.isEmailRegistered(email)
    }
}