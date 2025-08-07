package com.example.dulit.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// ===== TEMPORARY SCREENS (실제 feature 구현 전까지 임시 사용) =====

@Composable
private fun LoginScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "둘잇 로그인 화면",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                navController.navigate(Route.Home.route)
            }
        ) {
            Text("홈으로 이동")
        }
    }
}

@Composable
private fun RootTabScreen(navController : NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "둘잇 탭 화면",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                navController.navigate(Route.Login.route)
            }
        ) {
            Text("로그아웃")
        }
    }}


@Composable
private fun PostDetailScreen(postId: String) {
    Text("포스트 상세 화면: $postId")
}

// ===== TOP-LEVEL NAVIGATION =====

@Composable
fun DulitNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Route.Login.route
    ) {
        // 로그인 화면
        composable(Route.Login.route) {
            LoginScreen(navController)
        }

        // 메인 홈 (탭 화면들 포함)
        composable(Route.Home.route) {
            RootTabScreen(navController)
        }

        // 포스트 상세 화면
        composable(Route.PostDetail.route) { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId") ?: ""
            PostDetailScreen(postId = postId)
        }
    }
}