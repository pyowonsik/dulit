// ui/theme/Theme.kt

package com.example.dulit.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// 앱의 라이트 모드 색상 스키마 정의
private val DulitLightColorScheme = lightColorScheme(
    primary = Amber400,                 // 주요 버튼, 선택된 요소, 강조 색상
    onPrimary = White,                  // Primary 색상 위의 텍스트/아이콘
    primaryContainer = Yellow100,       // 주요 컨테이너 배경 (예: 선택된 칩)
    onPrimaryContainer = Amber700,      // Primary 컨테이너 위의 텍스트

    secondary = Amber700,               // 앱바 제목, 주요 아이콘 등
    onSecondary = White,                // Secondary 색상 위의 텍스트
    secondaryContainer = Yellow50,      // 더 연한 배경, 그라데이션 시작
    onSecondaryContainer = Amber900,    // Secondary 컨테이너 위의 텍스트

    background = White,                 // 앱의 기본 배경
    onBackground = Gray800,             // 배경 위의 기본 텍스트

    surface = White,                    // 카드, 다이얼로그 등 표면 색상
    onSurface = Gray800,                // 표면 위의 텍스트
    surfaceVariant = Gray100,           // 비활성, 연한 배경
    onSurfaceVariant = Gray600,         // 변형 표면 위의 텍스트 (예: 부제)

    outline = Gray300,                  // 테두리, 구분선
    outlineVariant = Gray200,           // 더 연한 테두리

    error = Red400,                     // 에러 색상
    onError = White,                    // 에러 색상 위의 텍스트
)

// 앱의 메인 테마
@Composable
fun DulitTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DulitLightColorScheme,
        typography = Typography, // Type.kt에서 정의한 타이포그래피 적용
        content = content
    )
}

// ColorScheme에 없는 커스텀 색상이나 그라데이션을 쉽게 사용하기 위한 확장 속성
val MaterialTheme.customColorScheme: CustomColorScheme
    @Composable
    get() = CustomColorScheme

object CustomColorScheme {
    val gradientBackground: Brush
        @Composable
        get() = Brush.verticalGradient(
            colors = listOf(Yellow50, White)
        )

    val success: Color
        @Composable
        get() = Green400

    val legacyLoginTitle: Color
        @Composable
        get() = DulitNavy
}