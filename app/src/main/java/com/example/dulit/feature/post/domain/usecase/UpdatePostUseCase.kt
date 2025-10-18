package com.example.dulit.feature.post.domain.usecase

import com.example.dulit.feature.post.domain.model.Post
import com.example.dulit.feature.post.domain.model.UpdatePostRequest
import com.example.dulit.feature.post.domain.repository.PostRepository
import javax.inject.Inject

/**
 * 게시글 수정 UseCase
 */
class UpdatePostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(postId: Int, request: UpdatePostRequest): Result<Post> {
        return postRepository.updatePost(postId, request)
    }
}





