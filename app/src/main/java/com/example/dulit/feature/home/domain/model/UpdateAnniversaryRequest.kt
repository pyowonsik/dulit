package com.example.dulit.feature.home.domain.model

/**
 * 기념일 수정 요청 Domain Model (모든 필드 optional)
 */
data class UpdateAnniversaryRequest(
    val title: String? = null,
    val date: String? = null  // "2025-02-07" 형식
)





