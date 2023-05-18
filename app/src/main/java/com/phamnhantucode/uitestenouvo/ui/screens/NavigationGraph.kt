package com.phamnhantucode.uitestenouvo.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun NavigationGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController = navController, startDestination = Screens.MainScreen.route) {
        composable(Screens.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(
            Screens.ApprovalMatrixScreen.route + "/{type}/{id}",
            arguments = listOf(
                navArgument("type") {
                    type = NavType.StringType
                },
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) {
            ApprovalMatrixScreen(
                navController = navController,
                argument = it.arguments
            )
        }
    }
}