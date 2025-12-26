package com.first.beauty.ui.login

import com.first.beauty.ui.login.LoginViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.first.beauty.R
import com.first.beauty.data.model.LoggedInUserView
import com.first.beauty.ui.theme.CharcoalGray
import com.first.beauty.ui.theme.CoolGray
import com.first.beauty.ui.theme.PorcelainWhite
import com.first.beauty.ui.theme.SoftIvory
import com.first.beauty.ui.theme.WarmTaupe
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.rememberCoroutineScope
import com.first.beauty.data.auth.GoogleAuthManager
import kotlinx.coroutines.launch
import androidx.compose.material3.HorizontalDivider


@Composable
fun LoginScreen(
    navController: NavController,
    onLoginSuccess: (LoggedInUserView) -> Unit,  // Accept LoggedInUserView here
    onRegisterClick: () -> Unit  // ✅ Add this
) {
    val context = LocalContext.current

    val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(context))

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var showRegisterDialog by remember { mutableStateOf(false) }
    var googleEmail by remember { mutableStateOf("") }

    val loginResult by loginViewModel.loginResult.observeAsState()
    val scope = rememberCoroutineScope()

    val googleClient = remember {
        GoogleAuthManager.getClient(context)
    }

    // 🔥 IMPORTANT: Always show account picker
    LaunchedEffect(key1 = true) {
        googleClient.signOut()
    }

    // 🔥 Launcher to handle Google Sign-In result
    val googleLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->

        if (result.resultCode != android.app.Activity.RESULT_OK || result.data == null) {
            errorMessage = "Google sign-in cancelled"
            return@rememberLauncherForActivityResult
        }

        // Call handleSignInResult from your GoogleAuthManager
        GoogleAuthManager.handleSignInResult(
            data = result.data, // pass as positional or named 'data'
            onSuccess = { email ->
                scope.launch {
                    val user = loginViewModel.loginWithGoogle(email)

                    if (user != null) {
                        // ✅ Registered user → go home
                        onLoginSuccess(user)
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    } else {
                        // 🔹 Not registered → show dialog
                        googleEmail = email
                        showRegisterDialog = true
                    }
                }
            },
            onError = {
                errorMessage = "Google sign-in failed"
            }
        )
    }


    // Observe normal login via API
    LaunchedEffect(loginResult) {
        loginResult?.let { result ->
            result.success?.let {
                onLoginSuccess(it)
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            }

            result.error?.let {
                errorMessage = context.getString(it)
            }
        }
    }

    // ------------------ UI START ---------------------
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
                    loginViewModel.login(username.trim(), password)
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
            HorizontalDivider(modifier = Modifier.weight(1f).height(1.dp), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
            Text(
                text = "or",
                color = CoolGray,
                style = MaterialTheme.typography.labelMedium
            )

            HorizontalDivider(modifier = Modifier.weight(1f).height(1.dp), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Google sign-in button
        Button(
            onClick = {
                googleLauncher.launch(googleClient.signInIntent)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_google_logo),
                contentDescription = "Google",
                tint = Color.Unspecified,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text("Sign in with Google", color = Color.Black)
        }


        if (showRegisterDialog) {
            AlertDialog(
                onDismissRequest = { showRegisterDialog = false },
                title = { Text("User not registered") },
                text = {
                    Text("This Google account is not registered. Please create an account.")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showRegisterDialog = false
                            navController.navigate("register?email=$googleEmail")
                        }
                    ) {
                        Text("Register")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showRegisterDialog = false }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
        Spacer(modifier = Modifier.height(20.dp))


        // Bottom Text - Register
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            val annotatedText = buildAnnotatedString {
                append("New to Schöne? ")
                pushStringAnnotation(tag = "REGISTER", annotation = "register")
                withStyle(style = SpanStyle(color = CharcoalGray, fontWeight = FontWeight.Medium, textDecoration = TextDecoration.Underline)) {
                    append("Create an account")
                }
                pop()
            }

            Text(
                text = annotatedText,
                style = TextStyle(color = CoolGray),
                modifier = Modifier.clickable {
                    annotatedText.getStringAnnotations("REGISTER", 0, annotatedText.length)
                        .firstOrNull()?.let {
                            navController.navigate("register")
                        }
                }
            )
        }
    }
}



