package com.phamnhantucode.uitestenouvo.ui.screens

sealed class Screens(val route: String) {

    object MainScreen: Screens("main_screen")
    object ApprovalMatrixScreen: Screens("approval_matrix_screen")
}
