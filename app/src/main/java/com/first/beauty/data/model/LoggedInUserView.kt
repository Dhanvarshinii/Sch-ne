package com.first.beauty.data.model

data class LoggedInUserView(
    val displayName: String,
    val gender: String
) {
    companion object {
        fun fromLoggedInUser(user: LoggedInUser): LoggedInUserView {
            return LoggedInUserView(
                displayName = user.displayName,
                gender = user.gender
            )
        }
    }
}
