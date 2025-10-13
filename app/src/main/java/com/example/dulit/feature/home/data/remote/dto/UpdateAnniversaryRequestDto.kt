package com.example.dulit.feature.home.data.remote.dto

import com.example.dulit.feature.home.domain.model.UpdateAnniversaryRequest
import com.google.gson.annotations.SerializedName

/**
 * 기념일 수정 요청 DTO (모든 필드 optional)
 */
data class UpdateAnniversaryRequestDto(
    @SerializedName("title")
    val title: String? = null,
    
    @SerializedName("date")
    val date: String? = null  // "2025-02-07" 형식
)

/**
 * Domain Model → DTO 변환
 */
fun UpdateAnniversaryRequest.toDto(): UpdateAnniversaryRequestDto {
    return UpdateAnniversaryRequestDto(
        title = title,
        date = date
    )
}

