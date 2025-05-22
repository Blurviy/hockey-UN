package com.hockey.ui.screens.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.hockey.ui.theme.HockeyTheme

class SignupActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HockeyTheme {
                SignupScreen()
            }
        }
    }
}