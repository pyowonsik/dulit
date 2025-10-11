package com.example.dulit.feature.post.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.dulit.core.ui.theme.Amber800
import com.example.dulit.core.ui.theme.DulitTheme
import com.example.dulit.core.ui.theme.customColorScheme

// 샘플 데이터 모델
data class Post(
    val id: Int,
    val userProfileEmoji: String = "💑",
    val userName: String = "행복한커플",
    val timeAgo: String = "2시간 전",
    val imageUrl: String = "https://picsum.photos/seed/picsum/400/250", // 웹 이미지 URL
    val title: String = "남산타워 데이트 코스 추천해요! 💕",
    val content: String = "오늘 남산타워에 다녀왔는데 너무 좋았어요! 야경이 정말 예쁘고 분위기도 너무 로맨틱했어요. 다들 한번 가보세요~",
//    val tags: List<String> = listOf("남산타워", "데이트코스", "야경"),
    val likes: Int = 128,
    val comments: Int = 24,
)

@Composable
fun PostScreen() {
    // [수정] DulitTheme으로 감싸서 디자인 시스템 적용
    DulitTheme {


        // 샘플 데이터
//        val categories = listOf("전체", "데이트 코스", "맛집", "여행", "기념일")
        var selectedCategory by remember { mutableStateOf("전체") }
        val posts = remember { List(5) { index -> Post(id = index) } }


        Surface(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    // [수정] 커스텀 그라데이션 배경 적용
                    .background(brush = MaterialTheme.customColorScheme.gradientBackground)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        PostScreenHeader(onWritePostClick = { /* 글쓰기 화면으로 이동 */ })
                        Spacer(modifier = Modifier.height(16.dp))
                    }
//                    item {
//                        CategoryChips(
//                            categories = categories,
//                            selectedCategory = selectedCategory,
//                            onCategorySelected = { category -> selectedCategory = category }
//                        )
//                        Spacer(modifier = Modifier.height(20.dp))
//                    }
                    items(posts) { post ->
                        EnhancedPostCard(post = post)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun PostScreenHeader(onWritePostClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Title Icon",
                // [수정] 테마 색상 적용
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "전체 데이트 여행",
                // [수정] 테마 스타일 및 색상 적용
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        }
        Surface(
            onClick = onWritePostClick,
            shape = RoundedCornerShape(20.dp),
            // [수정] 테마 색상 적용
            color = MaterialTheme.colorScheme.primaryContainer,
            shadowElevation = 4.dp,
            modifier = Modifier.shadow(
                elevation = 8.dp,
                spotColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                shape = RoundedCornerShape(20.dp)
            )
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Write Post",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "글쓰기",
                    // [수정] 테마 스타일 및 색상 적용
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

@Composable
fun CategoryChips(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(categories) { category ->
            CategoryChip(
                label = category,
                isSelected = category == selectedCategory,
                onClick = { onCategorySelected(category) }
            )
        }
    }
}

@Composable
fun CategoryChip(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        // [수정] 테마 색상 적용
        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
        border = BorderStroke(
            1.dp,
            if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
        ),
        shadowElevation = if (isSelected) 4.dp else 0.dp
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            // [수정] 테마 스타일 및 색상 적용
            style = if (isSelected) MaterialTheme.typography.labelLarge else MaterialTheme.typography.bodyMedium,
            color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
fun EnhancedPostCard(post: Post) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column {
            PostHeader(post)
            // Image(...) // 이미지 로딩 라이브러리 사용 부분
            PostContent(post)
        }
    }
}

@Composable
fun PostHeader(post: Post) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .border(2.dp, MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = post.userProfileEmoji, style = MaterialTheme.typography.bodyLarge)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = post.userName, style = MaterialTheme.typography.labelLarge)
            Text(text = post.timeAgo, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Icon(
            imageVector = Icons.Default.MoreHoriz,
            contentDescription = "More Options",
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun PostContent(post: Post) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = post.title, style = MaterialTheme.typography.titleSmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = post.content, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onBackground)
        Spacer(modifier = Modifier.height(16.dp))

//        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//            post.tags.forEach { tag ->
//                TagChip(label = tag)
//            }
//        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            InteractionButton(icon = Icons.Default.FavoriteBorder, label = post.likes.toString(), color = MaterialTheme.colorScheme.primary)
            InteractionButton(icon = Icons.Default.ChatBubbleOutline, label = post.comments.toString(), color = MaterialTheme.colorScheme.secondary)
//            InteractionButton(icon = Icons.Default.BookmarkBorder, label = "저장", color = Amber800) // 버튼별 고유 색상은 직접 지정
        }
    }
}

@Composable
fun TagChip(label: String) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Text(
            text = "#$label",
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun InteractionButton(icon: ImageVector, label: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = icon, contentDescription = label, tint = color, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
