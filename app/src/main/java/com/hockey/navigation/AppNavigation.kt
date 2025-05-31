package com.hockey.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
//import com.hockey.ui.screens.auth.AuthScreen
import com.hockey.ui.screens.auth.LoginScreen
import com.hockey.ui.screens.auth.SignupScreen
import com.hockey.ui.screens.events.EventScreen
import com.hockey.ui.screens.home.HomeScreen
import com.hockey.ui.screens.news.NewsAndUpdateScreen
import com.hockey.ui.screens.settings.SettingsScreen
import com.hockey.ui.screens.team.TeamManagementScreen
import com.hockey.ui.viewmodels.AuthViewModel

// // import com.hockey.ui.viewmodels.AuthViewModel

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Signup : Screen("signup")
    object ManagerMain : Screen("manager_main")
    object PlayerMain : Screen("player_main")
    object FanMain : Screen("fan_main")
    object AdminMain : Screen("admin_main")
    object EventDetails : Screen("event_details/{eventId}")
    object EventCreation : Screen("event_creation")
    object NewsCreation : Screen("news_creation")
    object TeamEdit : Screen("team_edit")
    object PlayerManagement : Screen("player_management")
    object PlayerRegistration : Screen("player_registration")
    object TeamRegistration : Screen("team_registration")
    object Messages : Screen("messages")
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login", builder = {
        composable("login") {
            LoginScreen(modifier, navController, authViewModel)
        }
        composable("signup") {
            SignupScreen(modifier, navController, authViewModel)
        }
        composable("home") {
            // HomeScreen(modifier)
            NavigationBar(role = "home")
        }
        composable("events") { EventScreen() }

        // Teams
        composable("teamManagement") { TeamManagementScreen(modifier, navController) }
        composable("playerManagement") {  }
        
        composable("updates") { NewsAndUpdateScreen() }
        composable("settings") { SettingsScreen() }
        composable("nav") { NavigationBar("home") }
    })
}


