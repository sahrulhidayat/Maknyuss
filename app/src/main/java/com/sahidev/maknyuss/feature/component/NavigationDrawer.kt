package com.sahidev.maknyuss.feature.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.sahidev.maknyuss.feature.favorite.navigateToFavorite

val menuItems: List<MenuItem> = listOf(
    MenuItem(
        title = Menu.FAVORITE.title,
        icon = Icons.Filled.Favorite
    )
)

fun NavController.navigateToMenu(item: MenuItem) {
    when (item.title) {
        Menu.FAVORITE.title -> {
            this.navigateToFavorite()
        }
    }
}

enum class Menu(val title: String) {
    FAVORITE("Favorite")
}

data class MenuItem(
    val title: String,
    val icon: ImageVector,
    val badgeCount: Int? = null
)

