package com.example.dulit.feature.post.domain.usecase

import com.example.dulit.feature.post.domain.model.PostLikeResponse
import com.example.dulit.feature.post.domain.repository.PostRepository
import javax.inject.Inject

/**
 * 게시글 좋아요 UseCase
 * @return 좋아요 상태 (isLike: true/false/null)
 */
class LikePostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(postId: Int): Result<PostLikeResponse> {
        return postRepository.likePost(postId)
    }
}

