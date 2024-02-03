package com.sahidev.maknyuss

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sahidev.maknyuss.feature.home.HomeScreen
import com.sahidev.maknyuss.ui.theme.MaknyussTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaknyussTheme {
                HomeScreen()
            }
        }
    }
}