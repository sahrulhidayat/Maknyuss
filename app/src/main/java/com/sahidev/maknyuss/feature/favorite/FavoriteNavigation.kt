package com.sahidev.maknyuss.feature.favorite

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val FAVORITE_ROUTE = "favorite"

fun NavController.navigateToFavorite(navOptions: NavOptions? = null) {
    this.navigate(FAVORITE_ROUTE, navOptions)
}

fun NavGraphBuilder.favoriteScreen(
    onBack: () -> Unit,
    onClickItem: (id: Int) -> Unit
) {
    composable(route = FAVORITE_ROUTE) {
        FavoriteScreen(onBack, onClickItem)
    }
}