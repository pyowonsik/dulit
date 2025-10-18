package com.example.dulit.core.model

/**
 * Page 기반 페이지네이션 요청 파라미터
 * 
 * @property page 페이지 번호 (기본값: 1)
 * @property take 가져올 데이터 개수 (기본값: 5)
 * 
 * @see [백엔드 DTO] src/common/dto/page-pagination.dto.ts
 */
data class PagePaginationRequest(
    val page: Int = 1,
    val take: Int = 5
) {
    /**
     * Query Parameter로 변환
     */
    fun toQueryMap(): Map<String, Int> {
        return mapOf(
            "page" to page,
            "take" to take
        )
    }

    /**
     * 페이지 유효성 검증
     */
    fun isValid(): Boolean {
        return page > 0 && take > 0
    }

    companion object {
        /**
         * 기본 요청 (첫 페이지)
         */
        fun default(take: Int = 5): PagePaginationRequest {
            return PagePaginationRequest(page = 1, take = take)
        }

        /**
         * 다음 페이지 요청
         */
        fun next(currentPage: Int, take: Int = 5): PagePaginationRequest {
            return PagePaginationRequest(page = currentPage + 1, take = take)
        }

        /**
         * 이전 페이지 요청
         */
        fun previous(currentPage: Int, take: Int = 5): PagePaginationRequest {
            return PagePaginationRequest(
                page = (currentPage - 1).coerceAtLeast(1),
                take = take
            )
        }
    }
}





