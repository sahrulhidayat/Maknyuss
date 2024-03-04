package com.sahidev.maknyuss.feature.component

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

@Composable
fun AdmobBanner(modifier: Modifier = Modifier) {
    var adWidth by remember { mutableIntStateOf(AdSize.FULL_WIDTH) }

    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                adWidth = coordinates.size.width
            }
            .semantics {
                contentDescription = "Banner ad"
            },
        factory = { context ->
            val adSize = AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth)
            AdView(context).apply {
                setAdSize(adSize)
                adUnitId = "ca-app-pub-9796581696289372/3584792649"
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}

var mInterstitialAd: InterstitialAd? = null

fun loadInterstitialAd(context: Context) {
    InterstitialAd.load(
        context,
        "ca-app-pub-9796581696289372/7128699258",
        AdRequest.Builder().build(),
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
            }
        }
    )
}

fun showInterstitialAd(context: Context, onAdDismissed: () -> Unit) {
    val activity = context.findActivity()

    if (mInterstitialAd != null && activity != null) {
        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdFailedToShowFullScreenContent(e: AdError) {
                mInterstitialAd = null
            }

            override fun onAdDismissedFullScreenContent() {
                mInterstitialAd = null

                loadInterstitialAd(context)
                onAdDismissed()
            }
        }
        mInterstitialAd?.show(activity)
    }
}

fun removeInterstitialAd() {
    mInterstitialAd?.fullScreenContentCallback = null
    mInterstitialAd = null
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}