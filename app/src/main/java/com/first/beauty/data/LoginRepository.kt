package com.first.beauty.data

class LoginRepository(private val dataSource: LoginDataSource) {
    suspend fun login(username: String, password: String): Result<Boolean> {
        return dataSource.login(username, password)
    }
}
