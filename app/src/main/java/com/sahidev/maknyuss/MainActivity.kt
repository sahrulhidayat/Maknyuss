package com.sahidev.maknyuss

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.DisposableEffect
import com.sahidev.maknyuss.navigation.AppNavHost
import com.sahidev.maknyuss.ui.theme.MaknyussTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT, Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                Color.BLACK, Color.BLACK
            )
        )
        super.onCreate(savedInstanceState)
        setContent {
            DisposableEffect(true) {
                onDispose {}
            }

            MaknyussTheme {
                AppNavHost()
            }
        }
    }
}