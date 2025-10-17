package com.example.dulit.feature.home.presentation

import DulitTopAppBar
import LoadingIndicator
import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dulit.core.ui.theme.DulitTheme
import com.example.dulit.feature.home.presentation.component.AnniversaryCard
import com.example.dulit.feature.home.presentation.component.CreateAnniversaryModal
import com.example.dulit.feature.home.presentation.component.CreatePlanModal
import com.example.dulit.feature.home.presentation.component.DatePlanItemRow
import com.example.dulit.feature.home.presentation.component.EmptyContent
import com.example.dulit.feature.home.presentation.component.PagerNavigationButtons
import com.example.dulit.feature.home.presentation.component.SectionCard
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
    DulitTheme {
        val context = LocalContext.current

        // anniversaryState 상태 감지
        val anniversaryState by anniversaryViewModel.anniversaryState.collectAsState()
        val anniversaries by anniversaryViewModel.anniversaries.collectAsState()

        // planState 상태 감지
        val planState by planViewModel.planState.collectAsState()
        val plans by planViewModel.plans.collectAsState()

        // 모달 관리 boolean 변수
        var showCreateAnniversaryModal by remember { mutableStateOf(false) }
        var showCreatePlanModal by remember { mutableStateOf(false) }

        // 초기 데이터 로드
        LaunchedEffect(Unit) {
            anniversaryViewModel.getAnniversaries()
            planViewModel.getPlans()
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
        val coroutineScope = rememberCoroutineScope()

        // <CHANGE> Check if data is loading
        val isLoading = anniversaryState is AnniversaryState.Loading ||
                planState is PlanState.Loading

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                DulitTopAppBar()

                // <CHANGE> Show loading indicator when data is being fetched
                if (isLoading) {
                    LoadingIndicator()
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.background,
                                        MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
                                    )
                                )
                            )
                    ) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp),
                            verticalArrangement = Arrangement.spacedBy(24.dp)
                        ) {
                            // D-DAY Section
                            item {
                                SectionCard(
                                    title = "D-DAY",
                                    onAddPressed = {
                                        showCreateAnniversaryModal = true
                                    }
                                ) {
                                    if (anniversaries.isEmpty()) {
                                        EmptyContent("기념일")
                                    } else {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.spacedBy(16.dp)
                                        ) {
                                            HorizontalPager(
                                                state = pagerState,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(140.dp),
                                                pageSpacing = 16.dp
                                            ) { index ->
                                                AnniversaryCard(
                                                    item = anniversaries[index],
                                                    modifier = Modifier.fillMaxWidth()
                                                )
                                            }

                                            PagerNavigationButtons(
                                                currentPage = pagerState.currentPage,
                                                totalPages = anniversaries.size,
                                                onPreviousClick = {
                                                    coroutineScope.launch {
                                                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                                    }
                                                },
                                                onNextClick = {
                                                    coroutineScope.launch {
                                                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                                    }
                                                }
                                            )
                                        }
                                    }
                                }
                            }

                            // When Date Section
                            item {
                                SectionCard(
                                    title = "When Date?",
                                    onAddPressed = {
                                        showCreatePlanModal = true
                                    }
                                ) {
                                    if (plans.isEmpty()) {
                                        EmptyContent("데이트")
                                    } else {
                                        Column(
                                            verticalArrangement = Arrangement.spacedBy(12.dp)
                                        ) {
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
        }

        // Modals
        if (showCreateAnniversaryModal) {
            CreateAnniversaryModal(
                onCreate = { title, date ->
                    Log.d(
                        "HomeScreen",
                        "onCreateAnniversary called with title: $title, date: $date"
                    )
                    anniversaryViewModel.createAnniversary(title, date)
                    showCreateAnniversaryModal = false
                },
                onDismiss = { showCreateAnniversaryModal = false }
            )
        }

        if (showCreatePlanModal) {
            CreatePlanModal(
                onCreate = { topic, location, dateTime ->
                    Log.d(
                        "HomeScreen",
                        "onCreatePlan called with title: $topic, location : $location,dateTime: $dateTime"
                    )
                     planViewModel.createPlan(topic, location, time = dateTime)
                    showCreatePlanModal = false
                },
                onDismiss = { showCreatePlanModal = false }
            )
        }
    }
}