package com.example.dulit.feature.post.domain.usecase

import com.example.dulit.feature.post.domain.repository.PostRepository
import javax.inject.Inject

/**
 * 게시글 삭제 UseCase
 * @return 삭제된 postId
 */
class DeletePostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(postId: Int): Result<Int> {
        return postRepository.deletePost(postId)
    }
}

