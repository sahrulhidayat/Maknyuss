package com.sahidev.maknyuss

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.DisposableEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.gms.ads.MobileAds
import com.sahidev.maknyuss.feature.component.loadInterstitialAd
import com.sahidev.maknyuss.feature.component.removeInterstitialAd
import com.sahidev.maknyuss.navigation.AppNavHost
import com.sahidev.maknyuss.ui.theme.MaknyussTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT, Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                Color.BLACK, Color.BLACK
            )
        )
        super.onCreate(savedInstanceState)

        MobileAds.initialize(this) {}
        loadInterstitialAd(this)

        setContent {
            DisposableEffect(true) {
                onDispose {}
            }

            MaknyussTheme {
                AppNavHost()
            }
        }
    }

    override fun onDestroy() {
        removeInterstitialAd()
        super.onDestroy()
    }
}