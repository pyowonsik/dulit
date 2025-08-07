package com.example.dulit.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// 둘잇 앱 색상 스키마 (Flutter 사용 색상 기반)
private val DulitColorScheme = lightColorScheme(
    // 주요 색상 - 메인 앰버/옐로우 테마
    primary = AMBER_400,                  // 메인 버튼, 선택 요소, 강조 색상
    onPrimary = WHITE,                   // 주요 색상 위의 텍스트
    primaryContainer = YELLOW_100,        // 주요 색상 컨테이너 배경
    onPrimaryContainer = AMBER_700,       // 주요 색상 컨테이너 위의 텍스트

    // 보조 색상 - 앰버 계열 지원 색상
    secondary = AMBER_700,               // 헤더, 제목, 앱바 텍스트
    onSecondary = WHITE,                // 보조 색상 위의 텍스트
    secondaryContainer = YELLOW_50,      // 연한 컨테이너, 선택된 탭 배경
    onSecondaryContainer = AMBER_700,    // 연한 컨테이너 위의 텍스트

    // 삼차 색상 - Flutter 원본의 핑크 악센트
    tertiary = TEXT_COLOR,              // 특별한 텍스트/요소를 위한 핑크 악센트
    onTertiary = WHITE,                 // 삼차 색상 위의 텍스트
    tertiaryContainer = PRIMARY_COLOR,   // 연분홍 컨테이너 배경
    onTertiaryContainer = BODY_TEXT_COLOR, // 분홍 배경 위의 네이비 텍스트

    // 배경 색상
    background = BACKGROUND_DEFAULT,     // 메인 앱 배경 (흰색)
    onBackground = GRAY_800,            // 배경 위의 메인 텍스트

    // 표면 색상 - 카드, 시트, 다이얼로그
    surface = SURFACE_COLOR,            // 카드 및 표면 배경 (흰색)
    onSurface = GRAY_800,              // 표면 위의 텍스트
    surfaceVariant = GRAY_100,          // 변형 표면 (연회색)
    onSurfaceVariant = GRAY_600,        // 변형 표면 위의 텍스트

    // 외곽선 색상 - 테두리와 구분선
    outline = GRAY_300,                 // 메인 테두리와 구분선
    outlineVariant = DIVIDER_COLOR,     // 연한 구분선과 분리자

    // 에러 색상
    error = ERROR_RED,                  // 에러 상태, 삭제 버튼
    onError = WHITE,                   // 에러 요소 위의 텍스트
    errorContainer = Color(0xFFFFDAD6), // 에러 컨테이너 배경
    onErrorContainer = Color(0xFF410002), // 에러 컨테이너 위의 텍스트

    // 반전 색상 (대비용)
    inversePrimary = AMBER_200,         // 어두운 배경용 반전 주요 색상
    inverseSurface = GRAY_800,          // 반전 표면
    inverseOnSurface = WHITE,          // 반전 표면 위의 텍스트

    // 스크림 색상 (모달/오버레이용)
    scrim = Color(0x80000000)          // 반투명 검정 오버레이
)

@Composable
fun DulitTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DulitColorScheme,
        typography = Typography,
        content = content
    )
}

// 커스텀 색상에 쉽게 접근하기 위한 확장 속성
val androidx.compose.material3.ColorScheme.customAmber: Color
    get() = AMBER_500

val androidx.compose.material3.ColorScheme.customYellow: Color
    get() = YELLOW_200

val androidx.compose.material3.ColorScheme.customGray: Color
    get() = GRAY_500

val androidx.compose.material3.ColorScheme.gradientStart: Color
    get() = GRADIENT_START

val androidx.compose.material3.ColorScheme.gradientEnd: Color
    get() = GRADIENT_END

val androidx.compose.material3.ColorScheme.successGreen: Color
    get() = SUCCESS_GREEN

val androidx.compose.material3.ColorScheme.warningOrange: Color
    get() = WARNING_ORANGE