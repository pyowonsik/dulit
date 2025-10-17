package com.example.dulit.feature.post.domain.usecase

import com.example.dulit.feature.post.domain.model.Post
import com.example.dulit.feature.post.domain.repository.PostRepository
import javax.inject.Inject

/**
 * 게시글 단건 조회 UseCase
 */
class GetPostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(postId: Int): Result<Post> {
        return postRepository.findOnePost(postId)
    }
}



