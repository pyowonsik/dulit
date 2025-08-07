package com.example.dulit.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Route(val route: String, val title: String? = null, val icon: ImageVector? = null) {

    // 최상위 Navigation 라우트
    object Login : Route(route = "login")
    object Root : Route(route = "root")

    // --- 하단 탭 레벨 라우트 ---
    object TabHome : Route("home", "홈", Icons.Default.Home)
    object TabChat : Route("chat", "채팅", Icons.Default.Send)
    object TabCalendar : Route("calendar", "캘린더", Icons.Default.DateRange)
    object TabPost : Route("post", "게시글", Icons.Default.Edit)
    object TabProfile : Route("profile", "프로필", Icons.Default.Person)

    companion object {
        val bottomNavItems = listOf(
            TabHome,
            TabChat,
            TabCalendar,
            TabPost,
            TabProfile
        )
    }
}
