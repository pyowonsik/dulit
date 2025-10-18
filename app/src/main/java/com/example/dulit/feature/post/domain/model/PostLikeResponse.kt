package com.example.dulit.feature.post.domain.model

/**
 * 좋아요/싫어요 응답 Domain Model
 * 
 * @property isLike null이면 좋아요 취소됨, true면 좋아요, false면 싫어요
 */
data class PostLikeResponse(
    val isLike: Boolean?
) {
    /**
     * 좋아요 상태인지 확인
     */
    val isLiked: Boolean
        get() = isLike == true

    /**
     * 싫어요 상태인지 확인
     */
    val isDisliked: Boolean
        get() = isLike == false

    /**
     * 좋아요/싫어요가 취소된 상태인지 확인
     */
    val isCancelled: Boolean
        get() = isLike == null
}





