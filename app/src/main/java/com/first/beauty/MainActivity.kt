package com.first.beauty

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.first.beauty.ui.login.*
import com.first.beauty.data.model.LoggedInUserView





//Don't change this
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyApp() {
    val navController = rememberNavController()
    var userGender by remember { mutableStateOf("default") } // initial default
    val context = LocalContext.current // ✅ This is safe, since we're inside a @Composable

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            // Only show bottom bar for these routes
            if (currentRoute in listOf("home", "routine", "concerns", "challenges")) {
                BottomNavBar(selectedTab) { selectedTab = it }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            NavHost(navController = navController, startDestination = "login") {
                composable("login") {
                    LoginScreen(
                        navController = navController,
                        onLoginSuccess = { user: LoggedInUserView ->
                            // Update the outer state
                            userGender = user.gender

                            // Set launcher icon dynamically here
                            setLauncherIcon(context, userGender)

                            navController.navigate("home") {
                                popUpTo("login") { inclusive = true }
                            }
                        },

                                onRegisterClick = {
                            navController.navigate("register")
                        }
                    )
                }
                composable("register") { RegisterScreen(navController) }
                composable("home") { HomeScreen() }
                composable("routine") { RoutineScreen() }
                composable("concerns") { ConcernsScreen() }
                composable("challenges") { ChallengesScreen() }
            }
        }
    }
}


@Composable
fun BottomNavBar(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    NavigationBar(
        containerColor = Color.White,
        contentColor = Color.Black
    ) {
        NavigationBarItem(
            icon = { Icon(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "Home") },
            label = { Text("Home") },
            selected = selectedTab == 0,
            onClick = { onTabSelected(0) }
        )
        NavigationBarItem(
            icon = { Icon(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "Routine") },
            label = { Text("Routine") },
            selected = selectedTab == 1,
            onClick = { onTabSelected(1) }
        )
        NavigationBarItem(
            icon = { Icon(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "Concerns") },
            label = { Text("Concerns") },
            selected = selectedTab == 2,
            onClick = { onTabSelected(2) }
        )
        NavigationBarItem(
            icon = { Icon(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "Challenges") },
            label = { Text("Challenges") },
            selected = selectedTab == 3,
            onClick = { onTabSelected(3) }
        )
    }
}

fun setLauncherIcon(context: Context, gender: String) {
    val packageManager = context.packageManager
    val default = ComponentName(context, "com.first.beauty.IconDefault")
    val male = ComponentName(context, "com.first.beauty.IconMale") // e.g., for Male
    val female = ComponentName(context, "com.first.beauty.IconFemale") // e.g., for Female

    // Disable all first
    packageManager.setComponentEnabledSetting(default, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP)
    packageManager.setComponentEnabledSetting(male, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP)
    packageManager.setComponentEnabledSetting(female, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP)

    // Enable one based on gender
    val selected = when (gender.lowercase()) {
        "male" -> male
        "female" -> female
        else -> default
    }

    packageManager.setComponentEnabledSetting(selected, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP)

    // Relaunch the launcher icon
    val intent = Intent(Intent.ACTION_MAIN).apply {
        addCategory(Intent.CATEGORY_HOME)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    context.startActivity(intent)
}




@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp()
}