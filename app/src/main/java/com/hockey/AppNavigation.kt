package com.hockey

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
//import com.hockey.ui.screens.auth.AuthScreen
import com.hockey.ui.screens.auth.LoginScreen
import com.hockey.ui.screens.auth.SignupScreen
import com.hockey.ui.screens.home.HomeScreen

@Composable
fun AppNavigation(modifier: Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController)
        }
        composable("signup") {
            SignupScreen(navController)
        }
        composable("home") {
            HomeScreen()
        }
    }
}


