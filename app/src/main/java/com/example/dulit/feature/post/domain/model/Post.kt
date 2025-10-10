package com.example.dulit.feature.post.domain.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * 게시글 Domain Model
 */
data class Post(
    val id: Int,
    val title: String,
    val description: String,
    val filePaths: List<String> = emptyList(),
    val author: PostAuthor,
    val couple: PostCouple?,  // ⭐ Int? → PostCouple? 변경
    val createdAt: String,
    val updatedAt: String
) {
    /**
     * 커플 ID (편의 속성)
     */
    val coupleId: Int?
        get() = couple?.id

    /**
     * 첫 번째 사진 경로 (썸네일용)
     */
    val firstPhotoPath: String?
        get() = filePaths.firstOrNull()

    /**
     * 사진 유무
     */
    val hasPhotos: Boolean
        get() = filePaths.isNotEmpty()

    /**
     * 사진 개수
     */
    val photoCount: Int
        get() = filePaths.size

    /**
     * 생성일 포맷 변환
     * @param pattern 날짜 형식 (기본: "yyyy-MM-dd HH:mm")
     */
    fun getFormattedCreatedAt(pattern: String = "yyyy-MM-dd HH:mm"): String {
        return try {
            val dateTime = LocalDateTime.parse(createdAt, DateTimeFormatter.ISO_DATE_TIME)
            dateTime.format(DateTimeFormatter.ofPattern(pattern))
        } catch (e: Exception) {
            createdAt
        }
    }

    /**
     * 수정일 포맷 변환
     * @param pattern 날짜 형식 (기본: "yyyy-MM-dd HH:mm")
     */
    fun getFormattedUpdatedAt(pattern: String = "yyyy-MM-dd HH:mm"): String {
        return try {
            val dateTime = LocalDateTime.parse(updatedAt, DateTimeFormatter.ISO_DATE_TIME)
            dateTime.format(DateTimeFormatter.ofPattern(pattern))
        } catch (e: Exception) {
            updatedAt
        }
    }
}
