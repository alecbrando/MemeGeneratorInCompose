package com.alecbrando.mememaker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.navDeepLink
import coil.annotation.ExperimentalCoilApi
import com.alecbrando.mememaker.Dashboard.DashboardScreen
import com.alecbrando.mememaker.ViewImage.ViewImageScreen
import com.alecbrando.mememaker.util.Constants.BASE_URL


@ExperimentalCoilApi
@Composable
fun Navigation(
    navController: NavHostController
) {
    val url = "https://cataas.com"
    NavHost(navController = navController, startDestination = "dashboard_route") {
        composable(route = "dashboard_route") {
            DashboardScreen(navController = navController)
        }

        composable(
            route = "viewImage_route?extension={extension}",
            deepLinks = listOf(navDeepLink { uriPattern = "$url/{extension}" })

        ) {
            val extension = remember {
                it.arguments?.getString("extension")
            }
            ViewImageScreen(navController = navController, extension = extension!!)
        }
    }
}



