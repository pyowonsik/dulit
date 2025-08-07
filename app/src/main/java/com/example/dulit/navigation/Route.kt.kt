package com.example.dulit.navigation

sealed class Route(val route: String) {

    // Top-level navigation routes
    object Login : Route("/")
    object Home : Route("/home")              // Main app with tabs
    object PostDetail : Route("/post/{postId}") {
        fun createRoute(postId: String): String = "/post/$postId"
    }

    // Tab-level routes (used inside RootTabScreen)
    object Anniversary : Route("/anniversary")
    object Calendar : Route("/calendar")
    object Chat : Route("/chat")
    object Profile : Route("/profile")
}