package com.example.dulit.feature.home.presentation

import SectionCard
import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dulit.core.ui.theme.DulitTheme
import com.example.dulit.core.ui.theme.customColorScheme
import com.example.dulit.feature.calendar.presentation.component.CreateCalendarModal
import com.example.dulit.feature.home.presentation.component.AnniversaryCard
import com.example.dulit.feature.home.presentation.component.CreateAnniversaryModal
import com.example.dulit.feature.home.presentation.component.DatePlanItemRow
import com.example.dulit.feature.home.presentation.component.PagerNavigationButtons
import com.example.dulit.feature.home.presentation.viewmodel.AnniversaryState
import com.example.dulit.feature.home.presentation.viewmodel.AnniversaryViewModel
import com.example.dulit.feature.home.presentation.viewmodel.PlanState
import com.example.dulit.feature.home.presentation.viewmodel.PlanViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    anniversaryViewModel: AnniversaryViewModel = hiltViewModel(),
    planViewModel: PlanViewModel = hiltViewModel()
) {
    // [수정] DulitTheme으로 감싸서 디자인 시스템 적용
    DulitTheme {

        val context = LocalContext.current

        /* anniversaryViewModel State , anniversaries 데이터 수집 */

        // anniversaryState 상태 감지
        val anniversaryState by anniversaryViewModel.anniversaryState.collectAsState()
        // anniversaries flow 데이터 접근 가능
        val anniversaries by anniversaryViewModel.anniversaries.collectAsState()

        // planState 상태 감지
        val planState by planViewModel.planState.collectAsState()
        // plans flow 데이터 접근 가능
        val plans by planViewModel.plans.collectAsState()

        // 모달 관리 boolean 변수
        var showCreateAnniversaryModal by remember { mutableStateOf(false) }
        var showCreatePlanModal by remember { mutableStateOf(false) }

        // 초기 데이터 로드 : Composable 처음 시작될 때 딱 1번만 실행
        LaunchedEffect(Unit) {
            anniversaryViewModel.getAnniversaries()
            planViewModel.getPlans()

            Log.d("HomeScreen", "anniversaries 데이터 로드 : ${anniversaries.toString()}")
        }

        LaunchedEffect(anniversaryState) {
            when (anniversaryState) {
                is AnniversaryState.Success -> {}
                is AnniversaryState.Error -> {}
                else -> {}
            }
        }

        LaunchedEffect(planState) {
            when (planState) {
                is PlanState.Success -> {}
                is PlanState.Error -> {}
                else -> {}
            }
        }


        // anniversaries 개수에 따른 페이지 state
        val pagerState = rememberPagerState(pageCount = { anniversaries.size })

        // suspend method 호출을 위한 coroutine scope
        val coroutineScope = rememberCoroutineScope()

        Surface(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
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
                            onAddPressed = {
                                showCreateAnniversaryModal = true
                            }
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                HorizontalPager(
                                    state = pagerState,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(160.dp),
                                ) { index ->
                                    AnniversaryCard(item = anniversaries[index])
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
                            onAddPressed = {}
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

        if (showCreateAnniversaryModal) {
            CreateAnniversaryModal(
                onCreate = { title, date ->
                    Log.d("HomeScreen", "onCreate called with title: $title, date: $date")
                    showCreateAnniversaryModal = false
                    anniversaryViewModel.createAnniversary(title, date);
                },
                onDismiss = { showCreateAnniversaryModal = false }
            )
        }

//        if (showCreatePlanModal) {
//        }
    }
}


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

//
//    val anniversary : Anniversary = remember {
//        Anniversary(
//            id = 1,
//            title = "우리 만난지",
//            date = "2022.08.04",
//            createdAt = "2025.02.07",
//            updatedAt = "2025.02.07"
//        )
//    }
//
//    val plans  = remember {
//        listOf(
//            Plan(
//                id = 1,
//                topic = "영화 보기",
//                location = "강남",
//                time = "2025.04.30",
//                createdAt = "2025.02.07",
//                updatedAt = "2025.02.07"
//            )
//        )
//    }