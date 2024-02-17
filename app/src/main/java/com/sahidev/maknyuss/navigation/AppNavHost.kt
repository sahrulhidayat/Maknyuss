package com.sahidev.maknyuss.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.sahidev.maknyuss.feature.home.HOME_ROUTE
import com.sahidev.maknyuss.feature.home.homeScreen
import com.sahidev.maknyuss.feature.info.INFO_ROUTE
import com.sahidev.maknyuss.feature.info.infoScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = HOME_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        homeScreen(
            onClickItem = { id ->
                navController.navigate("${INFO_ROUTE}/$id")
            }
        )
        infoScreen(onBack = { navController.popBackStack() })
    }
}