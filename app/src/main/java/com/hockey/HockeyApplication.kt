// Create a new file: HockeyApplication.kt (in your main package)
package com.hockey

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HockeyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}