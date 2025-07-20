package com.first.beauty.ui.login

import com.first.beauty.data.model.LoggedInUserView

data class LoginResult(
    val success: LoggedInUserView? = null,
    val error: Int? = null
)
