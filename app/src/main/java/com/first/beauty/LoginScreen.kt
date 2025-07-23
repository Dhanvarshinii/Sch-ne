package com.first.beauty

import LoginViewModel
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TextFieldDefaults // ✅ correct
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.first.beauty.data.model.LoggedInUserView
import com.first.beauty.ui.login.LoginResult
import com.first.beauty.ui.login.LoginViewModelFactory
import com.first.beauty.ui.theme.CharcoalGray
import com.first.beauty.ui.theme.CoolGray
import com.first.beauty.ui.theme.PorcelainWhite
import com.first.beauty.ui.theme.SoftIvory
import com.first.beauty.ui.theme.WarmTaupe


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
        loginResult?.let { result: LoginResult ->
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
            .background(SoftIvory)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Logo
        Image(
            painter = painterResource(id = R.drawable.ic_logo_default),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(100.dp)
                .padding(bottom = 16.dp)
        )

        // Welcome Text
        Text(
            text = "Welcome to Schöne", // Replace with actual app name
            style = MaterialTheme.typography.headlineSmall.copy(
                color = CharcoalGray
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Username Field
        Text(
            text = "Username or Email",
            style = MaterialTheme.typography.labelSmall,
            color = CoolGray,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(4.dp))

        TextField(
            value = username,
            onValueChange = { username = it },
            placeholder = { Text("Enter your username/email", color = CoolGray) },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = PorcelainWhite,
                focusedContainerColor = PorcelainWhite,
                unfocusedIndicatorColor = WarmTaupe,
                focusedIndicatorColor = CharcoalGray
            ),
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Field
        Text(
            text = "Password",
            style = MaterialTheme.typography.labelSmall,
            color = CoolGray,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(4.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Enter your password", color = CoolGray) },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = PorcelainWhite,
                focusedContainerColor = PorcelainWhite,
                unfocusedIndicatorColor = WarmTaupe,
                focusedIndicatorColor = CharcoalGray
            ),
            modifier = Modifier
                .fillMaxWidth()
        )


        // Forgot Password
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            TextButton(onClick = { navController.navigate("forgot_password") }) {
                Text("Forgot Password?", color = CharcoalGray)
            }
        }

        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                errorMessage, color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Log In button
        Button(
            onClick = {
                if (username.isNotBlank() && password.isNotBlank()) {
                    loginViewModel.login(username, password)
                    errorMessage = ""
                } else {
                    errorMessage = "Please fill all fields"
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = CharcoalGray,
                contentColor = PorcelainWhite
            )

        ) {
            Text("Log In")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ---- or ---- divider
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(modifier = Modifier.weight(1f))
            Text(
                text = "or",
                color = CoolGray,
                style = MaterialTheme.typography.labelMedium
            )

            Divider(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Sign in with Google button
        OutlinedButton(
            onClick = { /* TODO */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            border = BorderStroke(1.dp, WarmTaupe)
        ) {
            Text("Sign in with Google", color = CharcoalGray)
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Bottom Text - Register
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                buildAnnotatedString {
                    append("New to Schöne? ")
                    withStyle(
                        style = SpanStyle(
                            color = CharcoalGray,
                            fontWeight = FontWeight.Medium
                        )
                    ) {
                        append("Create an account")
                    }
                },
                color = CoolGray,
                modifier = Modifier.clickable {
                    navController.navigate("register")
                }
            )
        }
    }
}

