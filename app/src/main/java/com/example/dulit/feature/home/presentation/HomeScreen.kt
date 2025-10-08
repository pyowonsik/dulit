package com.example.dulit.feature.home.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dulit.core.ui.theme.Amber200
import com.example.dulit.core.ui.theme.DulitTheme
import com.example.dulit.core.ui.theme.customColorScheme
import kotlinx.coroutines.launch

// Data classes (DdayItem, DatePlanItem) are unchanged...
data class DdayItem(
    val title: String,
    val days: String,
    val date: String,
    val emoji: String,
)

data class DatePlanItem(
    val emoji: String,
    val title: String,
    val date: String,
)


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen() {
    // [수정] DulitTheme으로 감싸서 디자인 시스템 적용
    DulitTheme {
        // Sample data remains the same...
        val ddayItems = remember {
            listOf(
                DdayItem(title = "우리 만난지", days = "D+924", date = "2022.08.04", emoji = "❤️"),
                DdayItem(title = "여행", days = "D-13", date = "2025.04.02", emoji = "✈️"),
                DdayItem(title = "1000일", days = "D-76", date = "2025.06.27", emoji = "🎉"),
            )
        }
        val datePlans = remember {
            listOf(
                DatePlanItem(emoji = "🎬", title = "영화 보기", date = "2025년 2월 14일 (수)"),
                DatePlanItem(emoji = "🍚", title = "떡도리탕 먹으러 가기", date = "2025년 2월 15일 (목)"),
            )
        }

        val pagerState = rememberPagerState(pageCount = { ddayItems.size })
        val coroutineScope = rememberCoroutineScope()

        Surface(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    // [수정] 커스텀 그라데이션 배경 적용
                    .background(brush = MaterialTheme.customColorScheme.gradientBackground)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        SectionCard(
                            title = "D-DAY",
                            onAddPressed = { /* D-Day 추가 로직 */ }
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                HorizontalPager(
                                    state = pagerState,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(160.dp),
                                ) { page ->
                                    DdayCard(item = ddayItems[page])
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                ) {
                                    Button(
                                        onClick = {
                                            coroutineScope.launch {
                                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                            }
                                        },
                                        enabled = pagerState.currentPage > 0,
                                        // [수정] 테마 색상 적용
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.secondary
                                        ),
                                        shape = RoundedCornerShape(20.dp)
                                    ) { Text("Prev") }
                                    Button(
                                        onClick = {
                                            coroutineScope.launch {
                                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                            }
                                        },
                                        enabled = pagerState.currentPage < ddayItems.size - 1,
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.secondary
                                        ),
                                        shape = RoundedCornerShape(20.dp)
                                    ) { Text("Next") }
                                }
                            }
                        }
                    }
                    item {
                        SectionCard(
                            title = "When Date?",
                            onAddPressed = { /* 데이트 계획 추가 로직 */ }
                        ) {
                            Column {
                                datePlans.forEach { plan ->
                                    DatePlanItemRow(plan = plan)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SectionCard(
    title: String,
    onAddPressed: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = MaterialTheme.colorScheme.primaryContainer
            )
            .background(
                // [수정] 테마 색상 적용 (반투명)
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                // [수정] 테마 스타일 및 색상 적용
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            IconButton(onClick = onAddPressed) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Add",
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.secondaryContainer, CircleShape)
                        .padding(4.dp),
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        content()
    }
}

@Composable
fun DdayCard(item: DdayItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Text(
                text = item.title,
                modifier = Modifier.align(Alignment.CenterStart),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = item.emoji,
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 28.sp)
            )
            Column(
                modifier = Modifier.align(Alignment.CenterEnd),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = item.days,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.date,
                    style = MaterialTheme.typography.bodySmall,
                    color = Amber200 // 테마에 없는 연한 색상은 직접 사용
                )
            }
        }
    }
}

@Composable
fun DatePlanItemRow(plan: DatePlanItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(plan.emoji, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = plan.title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Text(
            text = plan.date,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
