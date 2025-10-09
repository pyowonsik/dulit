package com.example.dulit.feature.calendar.domain.model
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Calendar(
    val id: Int,
    val title: String,
    val description: String,
    val date: String,  // ISO 8601: "2025-03-30T00:00:00Z"
    val filePaths: List<String> = emptyList()
) {
//    /**
//     * ISO 8601 문자열을 LocalDate로 변환
//     * "2025-03-30T00:00:00Z" → LocalDate(2025, 3, 30)
//     */
//    fun getLocalDate(): LocalDate {
//        return LocalDate.parse(date.take(10))  // "2025-03-30" 부분만 파싱
//    }
//
//    /**
//     * 날짜를 원하는 형식으로 포맷
//     * @param pattern 날짜 형식 (예: "yyyy년 MM월 dd일")
//     * @return 포맷된 날짜 문자열
//     */
//    fun getFormattedDate(pattern: String = "yyyy-MM-dd"): String {
//        val formatter = DateTimeFormatter.ofPattern(pattern)
//        return getLocalDate().format(formatter)
//    }
//
//    /**
//     * 첫 번째 사진 경로 (없으면 null)
//     */
//    val firstPhotoPath: String?
//        get() = filePaths.firstOrNull()
//
//    /**
//     * 사진이 있는지 여부
//     */
//    val hasPhotos: Boolean
//        get() = filePaths.isNotEmpty()
}