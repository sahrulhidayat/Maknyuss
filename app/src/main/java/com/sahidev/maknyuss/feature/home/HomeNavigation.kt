package com.sahidev.maknyuss.feature.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sahidev.maknyuss.feature.component.MenuItem

const val HOME_ROUTE = "home"

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(HOME_ROUTE, navOptions)
}

fun NavGraphBuilder.homeScreen(
    onClickItem: (id: Int) -> Unit,
    navigateToMenu: (item: MenuItem) -> Unit
) {
    composable(route = HOME_ROUTE) {
        HomeScreen(
            onClickItem = onClickItem,
            navigateToMenu = navigateToMenu
        )
    }
}