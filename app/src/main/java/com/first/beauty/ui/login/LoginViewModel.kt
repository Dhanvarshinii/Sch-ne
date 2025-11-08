import android.content.Context
import androidx.lifecycle.*
import com.first.beauty.data.remote.ApiService
import com.first.beauty.data.remote.LoginRequest
import com.first.beauty.R
import com.first.beauty.data.model.LoggedInUserView
import com.first.beauty.data.repository.LoginDataSource
import com.first.beauty.data.repository.LoginRepository
import com.first.beauty.ui.login.LoginFormState
import com.first.beauty.ui.login.LoginResult
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginViewModel(private val context: Context) : ViewModel() {

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    private val _loginFormState = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginFormState

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:5001/") // Android emulator localhost
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(ApiService::class.java)

    // ✅ Add repository reference
    private val repository = LoginRepository(LoginDataSource(context))

    // ✅ Helper function for Google Sign-In email check
    suspend fun isEmailRegistered(email: String): Boolean {
        return repository.isEmailRegistered(email)
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                val response = api.loginUser(LoginRequest(username, password))
                if (response.success && response.user != null) {
                    val loggedInUserView = LoggedInUserView.fromLoggedInUser(response.user)
                    _loginResult.postValue(LoginResult(success = loggedInUserView))
                } else {
                    _loginResult.postValue(LoginResult(error = R.string.login_failed))
                }
            } catch (e: Exception) {
                _loginResult.postValue(LoginResult(error = R.string.login_failed))
            }
        }
    }


    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginFormState.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginFormState.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginFormState.value = LoginFormState(isDataValid = true)
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return username.isNotBlank()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 4
    }
}

