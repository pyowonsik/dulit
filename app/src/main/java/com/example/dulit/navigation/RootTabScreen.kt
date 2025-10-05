package com.example.dulit.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dulit.feature.calendar.presentaion.CalendarScreen
import com.example.dulit.feature.home.presentaion.HomeScreen
import com.example.dulit.feature.post.presentaion.PostScreen
import com.example.dulit.feature.profile.presentaion.ProfileScreen
import com.example.dulit.core.ui.component.BottomNavigationBar
import com.example.dulit.feature.chat.presentaion.ChatScreen


@Composable
fun RootTabScreen(
    // 로그아웃 기능을 위해 최상위 NavController를 전달받습니다.
    rootNavController: NavHostController
) {
    val tabNavController = rememberNavController() // 탭 내부 화면 전환을 위한 NavController

    Scaffold(
        bottomBar = {
            BottomNavigationBar(tabNavController)
        }
    ) { innerPadding ->
        NavHost(
            navController = tabNavController,
            startDestination = Route.TabHome.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Route.TabHome.route) {
                HomeScreen()
            }
            composable(Route.TabChat.route) {
                ChatScreen()
            }
            composable(Route.TabCalendar.route) {
                CalendarScreen()
            }
            composable(Route.TabPost.route) {
                PostScreen()
            }
            composable(Route.TabProfile.route) {
                // ProfileScreen에 로그아웃을 위한 rootNavController를 전달합니다.
                ProfileScreen(rootNavController = rootNavController)
            }
        }
    }
}