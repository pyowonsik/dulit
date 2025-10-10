package com.example.dulit.feature.home.domain.model

/**
 * 약속 수정 요청 Domain Model (모든 필드 optional)
 */
data class UpdatePlanRequest(
    val topic: String? = null,
    val location: String? = null,
    val time: String? = null  // ISO 8601 형식
)

