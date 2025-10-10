package com.example.dulit.feature.home.data.model

import com.google.gson.annotations.SerializedName

/**
 * 기념일 작성 요청 DTO
 */
data class CreateAnniversaryRequestDto(
    @SerializedName("title")
    val title: String,
    
    @SerializedName("date")
    val date: String  // "2025-02-07" 형식
)

/**
 * Domain Model → DTO 변환
 */
fun com.example.dulit.feature.home.domain.model.CreateAnniversaryRequest.toDto(): CreateAnniversaryRequestDto {
    return CreateAnniversaryRequestDto(
        title = title,
        date = date
    )
}

