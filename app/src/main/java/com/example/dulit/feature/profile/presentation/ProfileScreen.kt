package com.example.dulit.feature.profile.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.dulit.core.ui.theme.Amber200
import com.example.dulit.core.ui.theme.Amber600
import com.example.dulit.core.ui.theme.DulitTheme
import com.example.dulit.core.ui.theme.customColorScheme

@Composable
fun rememberAsyncImagePainter(model: Any) = ColorPainter(Color.LightGray)

@Composable
fun ProfileScreen(
    rootNavController: NavHostController
) {
    // [수정] DulitTheme으로 감싸서 디자인 시스템 적용
    DulitTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    // [수정] 커스텀 그라데이션 배경 적용
                    .background(brush = MaterialTheme.customColorScheme.gradientBackground)
                    .verticalScroll(rememberScrollState())
            ) {
                ProfileHeader()
                Spacer(modifier = Modifier.height(70.dp))
                UserInfoSection()
                Spacer(modifier = Modifier.height(16.dp))
                ProfileStatsSection()
                Spacer(modifier = Modifier.height(24.dp))
                ProfileActionsSection()
                Spacer(modifier = Modifier.height(24.dp))
                MyBadgesSection()
                Spacer(modifier = Modifier.height(24.dp))
                MenuSection(rootNavController)
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    text = "버전 1.0.0",
                    // [수정] 테마에서 텍스트 스타일과 색상 가져오기
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun ProfileHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(190.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(
                    // [수정] 테마 색상 사용
                    color = Amber200,
                    shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
                )
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 20.dp, end = 40.dp)
                    .size(40.dp)
                    .background(Color.White.copy(alpha = 0.2f), CircleShape)
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 30.dp, bottom = 20.dp)
                    .size(25.dp)
                    .background(Color.White.copy(alpha = 0.15f), CircleShape)
            )
        }
        Box(
            modifier = Modifier
                .offset(y = 70.dp)
                .shadow(
                    elevation = 15.dp,
                    shape = CircleShape,
                    spotColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                )
                .background(color = MaterialTheme.colorScheme.surface, shape = CircleShape)
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Box {
                Image(
                    painter = rememberAsyncImagePainter(model = "https://picsum.photos/200"),
                    contentDescription = "프로필 이미지",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .background(MaterialTheme.colorScheme.surface, CircleShape)
                        .padding(4.dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                        .padding(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "프로필 편집",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun UserInfoSection() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "사용자 이름",
            // [수정] 테마 스타일 적용
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Outlined.Email,
                contentDescription = "이메일",
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "user@example.com",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ProfileStatsSection() {
    Row(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(20.dp),
                spotColor = MaterialTheme.colorScheme.outlineVariant
            )
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(20.dp))
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        StatItem("게시글", "24", modifier = Modifier.weight(1f))
        StatDivider()
        StatItem("팔로워", "128", modifier = Modifier.weight(1f))
        StatDivider()
        StatItem("팔로잉", "56", modifier = Modifier.weight(1f))
    }
}

@Composable
private fun StatItem(label: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = value, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.secondary)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun StatDivider() {
    Divider(
        modifier = Modifier
            .height(30.dp)
            .width(1.dp), color = MaterialTheme.colorScheme.outlineVariant
    )
}

@Composable
private fun ProfileActionsSection() {
    Row(
        modifier = Modifier.padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ActionButton(
            label = "커플 연결",
            icon = Icons.Filled.Favorite,
            color = Amber600, // 버튼별 고유 색상은 직접 지정
            onClick = { /*TODO*/ },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun ActionButton(
    label: String, icon: ImageVector, color: Color,
    onClick: () -> Unit, modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = color.copy(alpha = 0.3f)
            )
            .background(
                brush = Brush.verticalGradient(colors = listOf(color, color.copy(alpha = 0.8f))),
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = label, tint = Color.White, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = label, style = MaterialTheme.typography.labelLarge.copy(color = Color.White))
        }
    }
}

@Composable
private fun MyBadgesSection() {
    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.width(8.dp))
            Text("나의 뱃지", style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp), color = MaterialTheme.colorScheme.onBackground)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            BadgeItem("💑", "러브버드")
            BadgeItem("🌟", "인기쟁이")
            BadgeItem("📸", "포토그래퍼")
            BadgeItem("🎁", "선물왕")
        }
    }
}

@Composable
private fun BadgeItem(emoji: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = CircleShape,
                    spotColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                )
                .background(MaterialTheme.colorScheme.surface, CircleShape)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(emoji, style = MaterialTheme.typography.headlineSmall)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(label, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium), color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun MenuSection(navController: NavHostController) {
    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(20.dp),
                spotColor = MaterialTheme.colorScheme.outlineVariant
            )
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
    ) {
        MenuItem("설정", Icons.Filled.Settings, MaterialTheme.colorScheme.primary) { /*TODO*/ }
        Divider(color = MaterialTheme.colorScheme.surfaceVariant)
        MenuItem("알림 설정", Icons.Filled.Notifications, MaterialTheme.colorScheme.secondary) { /*TODO*/ }
        Divider(color = MaterialTheme.colorScheme.surfaceVariant)
        MenuItem("개인정보 보호", Icons.Filled.Lock, Amber600) { /*TODO*/ }
    }
}

@Composable
private fun MenuItem(
    title: String, icon: ImageVector, iconColor: Color, onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(iconColor.copy(alpha = 0.1f), CircleShape)
                .padding(8.dp),
        ) {
            Icon(imageVector = icon, contentDescription = title, tint = iconColor, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium), color = MaterialTheme.colorScheme.onBackground)
        Spacer(modifier = Modifier.weight(1f))
    }
}
