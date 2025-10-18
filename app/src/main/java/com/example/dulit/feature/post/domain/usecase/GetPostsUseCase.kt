package com.example.dulit.feature.post.domain.usecase

import com.example.dulit.feature.post.domain.model.Post
import com.example.dulit.feature.post.domain.repository.PostRepository
import javax.inject.Inject

/**
 * 게시글 전체 조회 UseCase
 */
class GetPostsUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(
        page: Int? = null,
        take: Int? = null,
        order: String? = null
    ): Result<List<Post>> {
        return postRepository.findAllPosts(page, take, order)
    }
}





