package com.hockey.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.hockey.ui.theme.HockeyTheme

class PlayerRegistrationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HockeyTheme {
                PlayerRegistrationScreen()
            }
        }
    }
}
