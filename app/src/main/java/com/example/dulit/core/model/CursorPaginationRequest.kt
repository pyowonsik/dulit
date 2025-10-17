package com.example.dulit.core.model

/**
 * Cursor 기반 페이지네이션 요청 파라미터
 * 
 * @property cursor 페이지네이션의 커서 (예: "eyJ2YWx1ZXMiOnsiaWQiOjF9...")
 * @property order 정렬 순서 배열 (예: ["id_DESC", "likeCount_ASC"])
 * @property take 가져올 데이터 개수 (기본값: 10)
 * 
 * @see [백엔드 DTO] src/common/dto/cursor-pagination.dto.ts
 */
data class CursorPaginationRequest(
    val cursor: String? = null,
    val order: List<String> = listOf("id_DESC"),
    val take: Int = 10
) {
    /**
     * Query Parameter로 변환
     */
    fun toQueryMap(): Map<String, Any> {
        return buildMap {
            cursor?.let { put("cursor", it) }
            put("order", order.joinToString(","))
            put("take", take)
        }
    }

    companion object {
        /**
         * 기본 요청 (첫 페이지)
         */
        fun default(take: Int = 10): CursorPaginationRequest {
            return CursorPaginationRequest(
                cursor = null,
                order = listOf("id_DESC"),
                take = take
            )
        }

        /**
         * 다음 페이지 요청
         */
        fun next(cursor: String, take: Int = 10): CursorPaginationRequest {
            return CursorPaginationRequest(
                cursor = cursor,
                order = listOf("id_DESC"),
                take = take
            )
        }
    }
}



