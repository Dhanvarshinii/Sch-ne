package com.first.beauty.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val displayName: String,
    val gender: String,
    val username: String,
    val email: String,
    val country: String,
    val dob: String
)
