package com.sahidev.maknyuss.feature.about

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val ABOUT_ROUTE = "about"

fun NavController.navigateToAbout(navOptions: NavOptions? = null) {
    this.navigate(ABOUT_ROUTE, navOptions)
}

fun NavGraphBuilder.aboutScreen(
    onBack: () -> Unit,
) {
    composable(route = ABOUT_ROUTE) {
        AboutScreen(onBack)
    }
}