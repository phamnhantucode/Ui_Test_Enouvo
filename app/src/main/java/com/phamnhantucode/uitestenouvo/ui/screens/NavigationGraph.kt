package com.phamnhantucode.uitestenouvo.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController = navController, startDestination = Screens.MainScreen.route) {
        composable(Screens.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(Screens.ApprovalMatrixScreen.route) {
            ApprovalMatrixScreen(navController = navController)
        }
    }
}