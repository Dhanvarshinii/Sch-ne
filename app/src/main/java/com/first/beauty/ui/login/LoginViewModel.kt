package com.first.beauty.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import android.content.Context
import com.first.beauty.ui.login.LoginFormState
import com.first.beauty.R
import com.first.beauty.data.model.LoggedInUserView
import com.first.beauty.data.repository.LoginDataSource
import com.first.beauty.data.repository.LoginRepository
import com.first.beauty.data.repository.Result
import com.first.beauty.ui.login.LoginResult
import kotlinx.coroutines.launch

class LoginViewModel(private val context: Context) : ViewModel() {

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    private val _loginFormState = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginFormState

    private val repository = LoginRepository(LoginDataSource(context))

    // ------------------ Normal login ------------------
    fun login(username: String, password: String) {
        viewModelScope.launch {
            when (val result = repository.login(username.trim(), password.trim())) {
                is Result.Success -> {
                    val userResponse = result.data.user
                    if (userResponse != null) {
                        val userView = LoggedInUserView.fromLoggedInUser(userResponse)
                        _loginResult.postValue(LoginResult(success = userView))
                    } else {
                        _loginResult.postValue(LoginResult(error = R.string.login_failed))
                    }
                }
                is Result.Error -> {
                    _loginResult.postValue(LoginResult(error = R.string.login_failed))
                }
            }
        }
    }

    // ------------------ Google login ------------------
    suspend fun loginWithGoogle(email: String): LoggedInUserView? {
        val user = repository.getUserByEmail(email)
        return user?.let { LoggedInUserView.fromLoggedInUser(it) }
    }

    // ------------------ Check if email is registered (Google Sign-In) ------------------
    suspend fun checkEmailExists(email: String): Boolean {
        return repository.checkEmailExists(email)
    }

    // ------------------ Form validation ------------------
    fun loginDataChanged(username: String, password: String) {
        when {
            username.isBlank() -> _loginFormState.value = LoginFormState(usernameError = R.string.invalid_username)
            password.length < 4 -> _loginFormState.value = LoginFormState(passwordError = R.string.invalid_password)
            else -> _loginFormState.value = LoginFormState(isDataValid = true)
        }
    }
}
