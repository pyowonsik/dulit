package com.example.dulit.feature.post.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

// 샘플 데이터 모델
data class Post(
    val id: Int,
    val userProfileEmoji: String = "💑",
    val userName: String = "행복한커플",
    val timeAgo: String = "2시간 전",
    val imageUrl: String = "https://picsum.photos/seed/picsum/400/250",
    val title: String = "남산타워 데이트 코스 추천해요! 💕",
    val content: String = "오늘 남산타워에 다녀왔는데 너무 좋았어요! 야경이 정말 예쁘고 분위기도 너무 로맨틱했어요. 다들 한번 가보세요~",
    val likes: Int = 128,
    val comments: Int = 24,
)

/**
 * Post 카드 컴포넌트
 */
@Composable
fun PostCard(
    post: Post,
    modifier: Modifier = Modifier,
    onPostClick: () -> Unit = {},
    onLikeClick: () -> Unit = {},
    onCommentClick: () -> Unit = {},
    onMoreClick: () -> Unit = {}
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        onClick = onPostClick
    ) {
        Column {
            PostHeader(
                post = post,
                onMoreClick = onMoreClick
            )
            // TODO: 이미지 추가 시 여기에 AsyncImage 사용
            PostContent(
                post = post,
                onLikeClick = onLikeClick,
                onCommentClick = onCommentClick
            )
        }
    }
}

/**
 * Post 헤더 (프로필 + 이름 + 시간)
 */
@Composable
private fun PostHeader(
    post: Post,
    onMoreClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 프로필 아이콘
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = post.userProfileEmoji,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // 이름 + 시간
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = post.userName,
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = post.timeAgo,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // 더보기 버튼
        IconButton(onClick = onMoreClick) {
            Icon(
                imageVector = Icons.Default.MoreHoriz,
                contentDescription = "More Options",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * Post 내용 (제목 + 본문 + 인터랙션 버튼)
 */
@Composable
private fun PostContent(
    post: Post,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        // 제목
        Text(
            text = post.title,
            style = MaterialTheme.typography.titleSmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 본문
        Text(
            text = post.content,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 인터랙션 버튼들
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            InteractionButton(
                icon = Icons.Default.FavoriteBorder,
                label = post.likes.toString(),
                color = MaterialTheme.colorScheme.primary,
                onClick = onLikeClick
            )
            InteractionButton(
                icon = Icons.Default.ChatBubbleOutline,
                label = post.comments.toString(),
                color = MaterialTheme.colorScheme.secondary,
                onClick = onCommentClick
            )
        }
    }
}

/**
 * 인터랙션 버튼 (좋아요, 댓글 등)
 */
@Composable
private fun InteractionButton(
    icon: ImageVector,
    label: String,
    color: Color,
    onClick: () -> Unit = {}
) {
    Surface(
        onClick = onClick,
        color = Color.Transparent
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = color,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}





