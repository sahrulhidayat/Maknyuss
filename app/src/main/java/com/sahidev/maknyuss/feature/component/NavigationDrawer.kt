package com.sahidev.maknyuss.feature.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.sahidev.maknyuss.feature.about.navigateToAbout
import com.sahidev.maknyuss.feature.favorite.navigateToFavorite

val menuItems: List<MenuItem> = listOf(
    MenuItem(
        title = Menu.FAVORITE.title,
        icon = Icons.Outlined.FavoriteBorder
    ),
    MenuItem(
        title = Menu.ABOUT.title,
        icon = Icons.Outlined.Info
    )
)

fun NavController.navigateToMenu(item: MenuItem) {
    when (item.title) {
        Menu.FAVORITE.title -> {
            this.navigateToFavorite()
        }
        Menu.ABOUT.title -> {
            this.navigateToAbout()
        }
    }
}

enum class Menu(val title: String) {
    FAVORITE("My Favorites"),
    ABOUT("About")
}

data class MenuItem(
    val title: String,
    val icon: ImageVector,
    val badgeCount: Int? = null
)

