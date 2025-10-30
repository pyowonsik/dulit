package com.example.dulit.feature.home.presentation.component

import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dulit.core.ui.theme.Amber200
import com.example.dulit.feature.home.domain.model.Anniversary

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnniversaryCard(
    item: Anniversary,
    modifier: Modifier = Modifier,
    onEdit: (Anniversary) -> Unit,
    onDelete: (Anniversary) -> Unit
) {
    // DropdownMenu 상태 관리
    var showContextMenu by remember { mutableStateOf(false) }

    // <CHANGE> Added heartbeat animation for the icon
    val infiniteTransition = rememberInfiniteTransition(label = "heartbeat")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Box {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .combinedClickable(
                    onClick = { /* 일반 클릭 - 필요시 처리 */ },
                    onLongClick = { showContextMenu = true }
                ),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            // <CHANGE> Added gradient background overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                                MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.2f)
                            )
                        )
                    )
                    .padding(20.dp)
            ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // <CHANGE> Left section with title and subtitle
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 20.sp,
                            letterSpacing = 0.5.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "소중한 날",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                }

                // <CHANGE> Center icon with animated background
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .scale(scale)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    Color(0xFFFF6B9D).copy(alpha = 0.2f),
                                    Color(0xFFFFC3E1).copy(alpha = 0.1f)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Heart",
                        modifier = Modifier.size(28.dp),
                        tint = Color(0xFFFF6B9D)
                    )
                }

                // <CHANGE> Right section with date info
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = item.date,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 16.sp,
                            letterSpacing = 0.3.sp
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    val dDayText = remember(item.date) { item.getDDayText() }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Amber200.copy(alpha = 0.3f),
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Text(
                            text = dDayText,
                            style = MaterialTheme.typography.labelMedium,
                            color = Amber200,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }

        // 컨텍스트 메뉴 (Long Press 시 표시)
        DropdownMenu(
            expanded = showContextMenu,
            onDismissRequest = { showContextMenu = false }
        ) {
            // 수정 메뉴 아이템
            DropdownMenuItem(
                text = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "수정",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text("수정")
                    }
                },
                onClick = {
                    showContextMenu = false
                    onEdit(item)
                }
            )

            // 삭제 메뉴 아이템
            DropdownMenuItem(
                text = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "삭제",
                            tint = MaterialTheme.colorScheme.error
                        )
                        Text(
                            "삭제",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                onClick = {
                    showContextMenu = false
                    onDelete(item)
                }
            )
        }
    }


}