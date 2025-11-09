package com.first.beauty.ui.register

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.first.beauty.ui.theme.CharcoalGray
import com.first.beauty.ui.theme.CoolGray
import com.first.beauty.ui.theme.PorcelainWhite
import com.first.beauty.ui.theme.SoftIvory
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.first.beauty.data.remote.RegisterRequest
import com.first.beauty.data.remote.RegisterResponse
import com.first.beauty.data.remote.RetrofitClient
import com.first.beauty.setLauncherIcon
import com.first.beauty.utils.CountryUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.Period

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController) {
    val context = LocalContext.current

    // --- States ---
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var dobValue by remember { mutableStateOf(TextFieldValue("")) }
    var country by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("Female") }
    var expandedGender by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // --- Field Errors ---
    var firstNameError by remember { mutableStateOf("") }
    var usernameError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var dobError by remember { mutableStateOf("") }
    var countryError by remember { mutableStateOf("") }

    val genderOptions = listOf("Female", "Male", "Other", "Prefer not to say")
    val countryList = remember { CountryUtil.getCountries() }

    // --- Validation function ---
    fun validateForm(): Boolean {
        var isValid = true

        // Reset all errors
        firstNameError = ""
        usernameError = ""
        emailError = ""
        passwordError = ""
        dobError = ""
        countryError = ""
        errorMessage = ""

        val trimmedFirst = firstName.trim()
        val trimmedUsername = username.trim()
        val trimmedEmail = email.trim()
        val trimmedPassword = password.trim()
        val trimmedDob = dob.trim()
        val trimmedCountry = country.trim()

        // First name
        if (trimmedFirst.isEmpty()) {
            firstNameError = "First name is required"
            isValid = false
        }

        // Username: 5-15 chars, letters/numbers/underscore
        val usernamePattern = "^[A-Za-z0-9_]{5,15}$".toRegex()
        if (!usernamePattern.matches(trimmedUsername)) {
            usernameError = "Username: 5-15 chars, letters/numbers/underscore"
            isValid = false
        }

        // Email
        val emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
        if (!emailPattern.matches(trimmedEmail)) {
            emailError = "Enter a valid email"
            isValid = false
        }

        // Password: 8+ chars, upper, lower, digit, special
        val passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#\$%^&+=!]).{8,}$".toRegex()
        if (!passwordPattern.matches(trimmedPassword)) {
            passwordError = "Password must be 8+ chars, include upper, lower, digit & special char"
            isValid = false
        }

        // DOB: valid date and age >=16
        if (!trimmedDob.matches("""\d{2}/\d{2}/\d{4}""".toRegex())) {
            dobError = "Enter a valid date (DD/MM/YYYY)"
            isValid = false
        } else {
            try {
                val parts = trimmedDob.split("/")
                val day = parts[0].toInt()
                val month = parts[1].toInt()
                val year = parts[2].toInt()
                val birthDate = LocalDate.of(year, month, day)
                val age = Period.between(birthDate, LocalDate.now()).years
                if (age < 16) {
                    dobError = "You must be at least 16 years old"
                    isValid = false
                }
            } catch (e: Exception) {
                dobError = "Enter a valid date"
                isValid = false
            }
        }

        // Country
        if (trimmedCountry.isEmpty()) {
            countryError = "Country is required"
            isValid = false
        }

        return isValid
    }


    // --- UI ---
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftIvory)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Create your Schöne journey 🌷",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "Join now and start building your personal beauty routine.",
            fontSize = 15.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(16.dp))

        // First Name
        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First Name*", color = CoolGray) },
            modifier = Modifier.fillMaxWidth()
        )
        if (firstNameError.isNotEmpty()) {
            Text(firstNameError, color = Color.Red, fontSize = 12.sp)
        }

        Spacer(Modifier.height(8.dp))

        // Last Name (optional)
        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name", color = CoolGray) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        // Username
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username*", color = CoolGray) },
            modifier = Modifier.fillMaxWidth()
        )
        if (usernameError.isNotEmpty()) {
            Text(usernameError, color = Color.Red, fontSize = 12.sp)
        }

        Spacer(Modifier.height(8.dp))

        // Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email*", color = CoolGray) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        if (emailError.isNotEmpty()) {
            Text(emailError, color = Color.Red, fontSize = 12.sp)
        }

        Spacer(Modifier.height(8.dp))

        // Password
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password*", color = CoolGray) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        if (passwordError.isNotEmpty()) {
            Text(passwordError, color = Color.Red, fontSize = 12.sp)
        }

        Spacer(Modifier.height(8.dp))

        // DOB
        OutlinedTextField(
            value = dobValue,
            onValueChange = { input ->
                val digits = input.text.filter { it.isDigit() }.take(8)
                val formatted = buildString {
                    digits.forEachIndexed { index, c ->
                        append(c)
                        if (index == 1 || index == 3) append('/')
                    }
                }
                var cursor = input.selection.end
                if (cursor == 3 || cursor == 6) cursor += 1 // skip slashes
                if (cursor > formatted.length) cursor = formatted.length
                dobValue = TextFieldValue(
                    text = formatted,
                    selection = androidx.compose.ui.text.TextRange(cursor)
                )
                dob = if (formatted.length == 10) formatted else ""
            },
            label = { Text("Date of Birth*", color = CoolGray) },
            placeholder = { Text("DD/MM/YYYY") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        if (dobError.isNotEmpty()) {
            Text(dobError, color = Color.Red, fontSize = 12.sp)
        }

        Spacer(Modifier.height(8.dp))

        // Country
        CountrySelector(
            allCountries = countryList,
            selectedCountry = country,
            onCountrySelected = {  country = it }
        )
        if (countryError.isNotEmpty()) Text(countryError, color = Color.Red, fontSize = 12.sp)

        Spacer(Modifier.height(8.dp))

        // Gender
        ExposedDropdownMenuBox(
            expanded = expandedGender,
            onExpandedChange = { expandedGender = !expandedGender }
        ) {
            OutlinedTextField(
                value = selectedGender,
                onValueChange = {},
                label = { Text("Gender", color = CoolGray) },
                readOnly = true,
                modifier = Modifier.fillMaxWidth().menuAnchor()
            )
            ExposedDropdownMenu(expanded = expandedGender, onDismissRequest = { expandedGender = false }) {
                genderOptions.forEach { gender ->
                    DropdownMenuItem(onClick = {
                        selectedGender = gender
                        expandedGender = false
                    }, text = { Text(gender) })
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Backend error
        if (errorMessage.isNotEmpty()) Text(errorMessage, color = Color.Red, textAlign = TextAlign.Center)

        // Create Account Button
        Button(
            onClick = {
                if (!validateForm()) return@Button

                // Proceed with backend API call
                setLauncherIcon(context, selectedGender)
                val registerRequest = RegisterRequest(
                    name = "$firstName $lastName",
                    username = username,
                    email = email,
                    password = password,
                    country = country,
                    dob = dob,
                    gender = selectedGender
                )
                RetrofitClient.api.registerUser(registerRequest).enqueue(object : Callback<RegisterResponse> {
                    override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                        if (response.isSuccessful) {
                            val result = response.body()
                            if (result?.success == true) {
                                navController.navigate("home") { popUpTo("register") { inclusive = true } }
                            } else {
                                errorMessage = result?.message ?: "Unknown error"
                            }
                        } else errorMessage = "Server error: ${response.code()}"
                    }

                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        errorMessage = "Network error: ${t.localizedMessage}"
                    }
                })
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = CharcoalGray)
        ) {
            Text("Create Account", color = PorcelainWhite)
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Sign in link
        val annotatedText = buildAnnotatedString {
            append("Already a member? ")
            pushStringAnnotation(tag = "login", annotation = "login")
            withStyle(style = SpanStyle(color = CharcoalGray, fontWeight = FontWeight.Medium)) {
                append("Sign in here")
            }
            pop()
        }
        ClickableText(
            text = annotatedText,
            onClick = { offset ->
                annotatedText.getStringAnnotations(tag = "login", start = offset, end = offset)
                    .firstOrNull()?.let {
                        navController.navigate("login")
                    }
            },
            modifier = Modifier.padding(top = 8.dp),
            style = androidx.compose.ui.text.TextStyle(
                fontSize = 15.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        )
    }
}

@Composable
fun CountrySelector(
    allCountries: List<String>,
    selectedCountry: String,
    onCountrySelected: (String) -> Unit
) {
    var text by remember { mutableStateOf(selectedCountry) }
    var showSuggestions by remember { mutableStateOf(false) }

    LaunchedEffect(selectedCountry) {
        if (selectedCountry != text) text = selectedCountry
    }

    val filteredCountries = remember(text, allCountries) {
        if (text.isBlank()) emptyList()
        else allCountries.filter { it.contains(text, ignoreCase = true) }
            .sorted()
            .take(8)
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = text,
            onValueChange = { input ->
                text = input
                onCountrySelected(input)
                showSuggestions = input.isNotBlank() && filteredCountries.isNotEmpty()
            },
            label = { Text("Country*", color = CoolGray) },
            placeholder = { Text("Start typing...") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Show suggestions only if allowed
        if (showSuggestions) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                filteredCountries.forEach { country ->
                    Text(
                        text = country,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                text = country
                                showSuggestions = false
                                onCountrySelected(country) // notify parent
                            }
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewRegister() {
    val navController = rememberNavController()
    RegisterScreen(navController = navController)
}