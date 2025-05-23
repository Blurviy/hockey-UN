//// Create a new file, e.g., ui/screens/auth/AuthScreen.kt
//package com.hockey.ui.screens.auth // Or wherever your auth screens are
//
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.navigation.NavController
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//
//// Option 1A: AuthScreen as a simple router/container
//@Composable
//fun AuthScreen(
//    modifier: Modifier = Modifier,
//    appNavController: NavHostController // This is the main app's NavController
//) {
//    // This NavController is for navigation *within* the auth flow (e.g., login -> signup)
//    val authNavController = rememberNavController()
//
//    NavHost(navController = authNavController, startDestination = "login_internal") {
//        composable("login_internal") {
//            // Pass the appNavController if LoginScreen needs to navigate outside the auth flow
//            // (e.g., after successful login)
//            LoginScreen(
//                onLoginSuccess = {
//                    appNavController.navigate("home") {
//                        popUpTo("auth") { inclusive = true } // Clear auth flow
//                    }
//                },
//                onNavigateToSignup = {
//                    authNavController.navigate("signup_internal")
//                }
//            )
//        }
//        composable("signup_internal") {
//            // Pass the appNavController if SignupScreen needs to navigate outside the auth flow
//            SignupScreen(
//                navController = appNavController, // For navigating to "home" after signup
//                onNavigateBackToLogin = {
//                    authNavController.popBackStack()
//                }
//            )
//        }
        // You could add "forgot_password_internal" here later
//    }
//}

// Then in your LoginScreen.kt:
/*
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToSignup: () -> Unit,
    authViewModel: AuthViewModel = viewModel()
) {
    // ... your UI ...
    Button(onClick = {
        authViewModel.login(email, password) { success, _ ->
            if (success) {
                onLoginSuccess() // Call this to navigate to "home"
            } else { /* show error */ }
        }
    }) { Text("Login") }

    TextButton(onClick = onNavigateToSignup) { // Navigate to signup within auth flow
        Text("Don't have an account? Sign Up")
    }
}
*/

// And in your SignupScreen.kt, you'd already be passing the appNavController
// to navigate to "home". You might add a parameter for navigating back to login:
/*
@Composable
fun SignupScreen(
    navController: NavController, // This is the appNavController
    onNavigateBackToLogin: () -> Unit, // New parameter
    authViewModel: AuthViewModel = viewModel(),
) {
    // ...
    TextButton(onClick = onNavigateBackToLogin) {
        Text("Already have an account? Login")
    }
}
*/