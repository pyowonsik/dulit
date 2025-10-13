package com.example.dulit.feature.post.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dulit.core.ui.theme.DulitTheme
import com.example.dulit.core.ui.theme.customColorScheme
import com.example.dulit.feature.post.presentation.component.Post
import com.example.dulit.feature.post.presentation.component.PostCard
import com.example.dulit.feature.post.presentation.component.PostScreenHeader

/**
 * Post 메인 화면
 */
@Composable
fun PostScreen() {
    DulitTheme {
        // 샘플 데이터
        val posts = remember { List(5) { index -> Post(id = index) } }

        Surface(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = MaterialTheme.customColorScheme.gradientBackground)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    // 헤더
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        PostScreenHeader(
                            onWritePostClick = {
                                // TODO: 글쓰기 화면으로 이동
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // 포스트 리스트
                    items(posts) { post ->
                        PostCard(
                            post = post,
                            onPostClick = {
                                // TODO: 포스트 상세 화면으로 이동
                            },
                            onLikeClick = {
                                // TODO: 좋아요 처리
                            },
                            onCommentClick = {
                                // TODO: 댓글 화면으로 이동
                            },
                            onMoreClick = {
                                // TODO: 더보기 메뉴 표시
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}
