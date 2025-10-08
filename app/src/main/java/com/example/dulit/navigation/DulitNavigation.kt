package com.example.dulit.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dulit.feature.auth.presentation.LoginScreen

@Composable
fun DulitNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.Login.route
    ) {
        composable(Route.Login.route) {
//            LoginScreen(navController = navController)
            LoginScreen(navController = navController)
        }
        composable(Route.Root.route) {
            // RootTabScreen에 최상위 navController를 전달 (로그아웃 기능 때문)
            RootTabScreen(rootNavController = navController)
        }
    }
}