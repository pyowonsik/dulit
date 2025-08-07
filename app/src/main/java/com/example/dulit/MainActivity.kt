package com.example.dulit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.dulit.core.ui.theme.DulitTheme

import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.example.dulit.navigation.DulitNavigation


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
