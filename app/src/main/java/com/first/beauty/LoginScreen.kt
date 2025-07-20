package com.first.beauty

import LoginViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.first.beauty.data.model.LoggedInUserView
import com.first.beauty.ui.login.*


@Composable
fun LoginScreen(
    navController: NavController,
    onLoginSuccess: (LoggedInUserView) -> Unit,  // Accept LoggedInUserView here
    onRegisterClick: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val context = LocalContext.current
    val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(context))
    val loginResult by loginViewModel.loginResult.observeAsState()

    LaunchedEffect(loginResult) {
        loginResult?.let { result:LoginResult ->
            if (result.success != null) {
                onLoginSuccess(result.success)
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            } else if (result.error != null) {
                errorMessage = context.getString(result.error)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Add Icon at top
        Image(
            painter = painterResource(id = R.drawable.ic_logo_default),  // replace with your icon resource name
            contentDescription = "App Icon",
            modifier = Modifier
                .size(100.dp)
                .padding(bottom = 32.dp)

        )

        Text("Login", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(errorMessage, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (username.isNotBlank() && password.isNotBlank()) {
                    loginViewModel.login(username, password)
                    errorMessage = ""
                } else {
                    errorMessage = "Please fill all fields"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Log In")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Register text below login button
        Text(
            text = "Register for new users",
            color = MaterialTheme.colorScheme.primary,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .clickable { onRegisterClick() }
                .padding(8.dp)
        )

    }
}

