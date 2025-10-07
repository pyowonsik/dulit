package com.example.dulit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.dulit.core.ui.theme.DulitTheme
import com.example.dulit.navigation.DulitNavigation
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint  // ⭐ 추가
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DulitTheme {
                DulitNavigation()
            }
        }
    }
}
