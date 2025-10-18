package com.example.dulit.feature.post.domain.usecase

import com.example.dulit.feature.post.domain.model.CreatePostRequest
import com.example.dulit.feature.post.domain.model.Post
import com.example.dulit.feature.post.domain.repository.PostRepository
import javax.inject.Inject

/**
 * 게시글 작성 UseCase
 */
class CreatePostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(request: CreatePostRequest): Result<Post> {
        return postRepository.createPost(request)
    }
}





