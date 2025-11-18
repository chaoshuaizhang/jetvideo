package com.jetbrains.kmpapp

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.jetbrains.kmpapp.nav.AppNavHost
import com.jetbrains.kmpapp.nav.route.executeNavigationPageRegister


@Composable
fun App() {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    ) {
        executeNavigationPageRegister()
        AppNavHost()
    }
}
