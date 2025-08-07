package com.example.dulit.core.ui.theme

import androidx.compose.ui.graphics.Color

// ===== 기존 Flutter 앱에서 정의된 기본 색상들 =====
val PRIMARY_COLOR = Color(0xFFFAEFEF)      // 연분홍 배경색
val SECONDARY_COLOR = Color(0xFFF5F5F5)    // 연회색 배경색
val BODY_TEXT_COLOR = Color(0xFF3E5879)    // 진한 네이비 본문 텍스트
val TEXT_COLOR = Color(0xFFE48586)         // 핑크 텍스트 (제목 등)
val SUB_TEXT_COLOR = Color(0xFFFFE6E6)     // 연분홍 서브 텍스트

// ===== 실제 앱에서 사용되는 Yellow/Amber 색상 계열 =====
val YELLOW_50 = Color(0xFFFFFBE6)          // 연노랑 그라데이션 시작, 선택된 탭 배경
val YELLOW_100 = Color(0xFFFFF3CD)         // 연노랑 배경 (포스트 카테고리 버튼)
val YELLOW_200 = Color(0xFFFFE69C)         // 연노랑 그림자, 테두리
val AMBER_200 = Color(0xFFFFE082)          // 프로필 배경, 탭 테두리
val AMBER_300 = Color(0xFFFFD54F)          // 포스트 카테고리 테두리
val AMBER_400 = Color(0xFFFFCA28)          // 버튼 색상 (편집, 좋아요 등)
val AMBER_500 = Color(0xFFFFC107)          // 설정 아이콘
val AMBER_600 = Color(0xFFFFB300)          // 진한 버튼 색상
val AMBER_700 = Color(0xFFFFA000)          // 제목, 아이콘, 텍스트 (앱바, 탭 등)

// ===== Gray 색상 계열 =====
val GRAY_100 = Color(0xFFF5F5F5)          // 구분선 (연한)
val GRAY_200 = Color(0xFFEEEEEE)          // 구분선, 그림자, 탭바 그림자
val GRAY_300 = Color(0xFFE0E0E0)          // 비선택 테두리
val GRAY_400 = Color(0xFFBDBDBD)          // 화살표 아이콘
val GRAY_500 = Color(0xFF9E9E9E)          // 푸터 텍스트
val GRAY_600 = Color(0xFF757575)          // 일반 텍스트, 비선택 아이콘
val GRAY_700 = Color(0xFF616161)          // 서브 텍스트
val GRAY_800 = Color(0xFF424242)          // 진한 텍스트

// ===== 기본 색상 =====
val WHITE = Color(0xFFFFFFFF)             // 흰색 배경, 카드 배경
val BLACK = Color(0xFF000000)             // 검정색
val RED_400 = Color(0xFFF44336)           // 로그아웃, 에러 색상
val RED = Color(0xFFFF0000)               // 하트 이모지 등

// ===== 투명도가 적용된 색상들 =====
val TRANSLUCENT_NAVY = Color(0x804F709C)  // 50% 투명도 네이비 (login_screen에서 사용)
val WHITE_20_OPACITY = Color(0x33FFFFFF)  // 20% 투명 흰색 (프로필 원형 배경)
val WHITE_15_OPACITY = Color(0x26FFFFFF)  // 15% 투명 흰색 (프로필 원형 배경)
val AMBER_100_50_OPACITY = Color(0x80FFF3CD) // 50% 투명 앰버100 (그림자)
val YELLOW_100_50_OPACITY = Color(0x80FFF3CD) // 50% 투명 옐로우100 (그림자)
val YELLOW_200_30_OPACITY = Color(0x4DFFE69C) // 30% 투명 옐로우200 (그림자)

// ===== 시멘틱 색상 =====
val SUCCESS_GREEN = Color(0xFF4CAF50)     // 성공 상태
val ERROR_RED = RED_400                   // 에러 상태
val WARNING_ORANGE = Color(0xFFFF9800)    // 경고 상태

// ===== 테마별 기본 색상 =====
val BACKGROUND_DEFAULT = WHITE            // 기본 배경
val SURFACE_COLOR = WHITE                 // 카드, 표면 색상
val DIVIDER_COLOR = GRAY_200             // 구분선
val CARD_SHADOW = GRAY_200               // 카드 그림자

// ===== 그라데이션용 색상 =====
val GRADIENT_START = YELLOW_50           // 연노랑 그라데이션 시작
val GRADIENT_END = WHITE                 // 흰색 그라데이션 끝

// ===== 특수 투명 색상 =====
val TRANSPARENT = Color(0x00000000)      // 완전 투명
