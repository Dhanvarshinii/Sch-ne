package com.first.beauty

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.first.beauty.data.model.LoggedInUserView
import com.first.beauty.ui.home.ChallengesScreen
import com.first.beauty.ui.home.ConcernsScreen
import com.first.beauty.ui.home.HomeScreen
import com.first.beauty.ui.home.RoutineScreen
import com.first.beauty.ui.login.LoginScreen
import com.first.beauty.ui.onboarding.GettingStartedPage
import com.first.beauty.ui.register.RegisterScreen


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
                BottomNavBar(
                    selectedTab = selectedTab,
                    onTabSelected = { index ->
                        selectedTab = index
                        when (index) {
                            0 -> navController.navigate("home")
                            1 -> navController.navigate("routine")
                            2 -> navController.navigate("concerns")
                            3 -> navController.navigate("challenges")
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            NavHost(navController = navController, startDestination = "getting_started") {
                composable("getting_started") {
                    GettingStartedPage(navController = navController)
                }
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
                composable("home") { HomeScreen(userName = "Test1") }
                composable("routine") { RoutineScreen() }
                composable("concerns") {
                    ConcernsScreen(
                        userConcerns = listOf("Dry Skin", "Hyperpigmentation"),
                        userAllergies = "Peanuts, Parabens"
                    )
                }
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
            icon = { Icon(painter = painterResource(id = R.drawable.icons8_home_2),
                contentDescription = "Home",
                modifier = Modifier.size(36.dp)) },
            label = { Text("Home") },
            selected = selectedTab == 0,
            onClick = { onTabSelected(0) }
        )
        NavigationBarItem(
            icon = { Icon(painter = painterResource(id = R.drawable.icons8_add_square),
                contentDescription = "Routine",
                modifier = Modifier.size(28.dp)) },
            label = { Text("Routine") },
            selected = selectedTab == 1,
            onClick = { onTabSelected(1) }
        )
        NavigationBarItem(
            icon = { Icon(painter = painterResource(id = R.drawable.icons8_search_1),
                contentDescription = "Concerns",
                modifier = Modifier.size(28.dp)) },
            label = { Text("Concerns") },
            selected = selectedTab == 2,
            onClick = { onTabSelected(2) }
        )
        NavigationBarItem(
            icon = { Icon(painter = painterResource(id = R.drawable.icons8_challenge),
                contentDescription = "Challenges",
                modifier = Modifier.size(28.dp)) },
            label = { Text("Challenges") },
            selected = selectedTab == 3,
            onClick = { onTabSelected(3) }
        )
    }
}

// Dynamically the app icon changes
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

}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp()
}