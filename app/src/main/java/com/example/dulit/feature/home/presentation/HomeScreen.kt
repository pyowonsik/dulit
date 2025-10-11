package com.example.dulit.feature.home.presentation

import SectionCard
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dulit.core.ui.theme.DulitTheme
import com.example.dulit.core.ui.theme.customColorScheme
import com.example.dulit.feature.home.domain.model.Anniversary
import com.example.dulit.feature.home.domain.model.Plan
import com.example.dulit.feature.home.presentation.component.AnniversaryCard
import com.example.dulit.feature.home.presentation.component.DatePlanItemRow
import com.example.dulit.feature.home.presentation.component.PagerNavigationButtons
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

//        // Sample data remains the same...
//        val ddayItems = remember {
//            listOf(
//                DdayItem(title = "우리 만난지", days = "D+924", date = "2022.08.04", emoji = "❤️"),
//                DdayItem(title = "여행", days = "D-13", date = "2025.04.02", emoji = "✈️"),
//                DdayItem(title = "1000일", days = "D-76", date = "2025.06.27", emoji = "🎉"),
//            )
//        }

//        val datePlans = remember {
//            listOf(
//                DatePlanItem(emoji = "🎬", title = "영화 보기", date = "2025년 2월 14일 (수)"),
//                DatePlanItem(emoji = "🍚", title = "떡도리탕 먹으러 가기", date = "2025년 2월 15일 (목)"),
//            )
//        }


        // ViewModel 데이터로 수정
        // ViewModel에서 flow MutableListOf로 데이터 crud 달아주면 됨.
        val anniversary : Anniversary = remember {
            Anniversary(
                id = 1,
                title = "우리 만난지",
                date = "2022.08.04",
                createdAt = "2025.02.07",
                updatedAt = "2025.02.07"
            )
        }

        val plans  = remember {
            listOf(
                Plan(
                    id = 1,
                    topic = "영화 보기",
                    location = "강남",
                    time = "2025.04.30",
                    createdAt = "2025.02.07",
                    updatedAt = "2025.02.07"
                )
            )
        }

        val pagerState = rememberPagerState(pageCount = { 1 })
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
                                    AnniversaryCard(item = anniversary)
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                ) {
                                    PagerNavigationButtons(
                                        currentPage = pagerState.currentPage,
                                        totalPages = 1,
                                        onPreviousClick = {
                                            coroutineScope.launch {
                                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                            }
                                        },
                                        onNextClick = {
                                            coroutineScope.launch {
                                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                            }
                                        },
                                    )
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
                                plans.forEach { plan ->
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
