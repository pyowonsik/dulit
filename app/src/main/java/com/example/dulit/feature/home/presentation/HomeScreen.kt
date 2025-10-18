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
import com.example.dulit.feature.home.presentation.component.EmptyContent
import com.example.dulit.feature.home.presentation.component.MoreCard
import com.example.dulit.feature.home.presentation.component.PlanCard
import com.example.dulit.feature.home.presentation.component.SectionCard
import com.example.dulit.feature.home.presentation.component.ViewAllAnniversariesModal
import com.example.dulit.feature.home.presentation.component.ViewAllPlansModal
import com.example.dulit.feature.home.presentation.viewmodel.AnniversaryState
import com.example.dulit.feature.home.presentation.viewmodel.AnniversaryViewModel
import com.example.dulit.feature.home.presentation.viewmodel.PlanState
import com.example.dulit.feature.home.presentation.viewmodel.PlanViewModel

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
        var showAllAnniversariesModal by remember { mutableStateOf(false) }
        var showAllPlansModal by remember { mutableStateOf(false) }

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

        // anniversaries 개수에 따른 페이지 state (5개 초과 시 더보기 카드 추가)
        val anniversaryPagerState = rememberPagerState(pageCount = {
            if (anniversaries.size > 5) 6 else anniversaries.size
        })

        // plans 개수에 따른 페이지 state (5개 초과 시 더보기 카드 추가)
        val planPagerState = rememberPagerState(pageCount = {
            if (plans.size > 5) 6 else plans.size
        })

        // <CHANGE> Check if data is loading
        val isLoading =
            anniversaryState is AnniversaryState.Loading || planState is PlanState.Loading

        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
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
                                    title = "D-DAY", onAddPressed = {
                                        showCreateAnniversaryModal = true
                                    }) {
                                    if (anniversaries.isEmpty()) {
                                        EmptyContent("기념일")
                                    } else {
                                        // 좌우 스크롤로 페이지 전환
                                        HorizontalPager(
                                            state = anniversaryPagerState,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(140.dp),
                                            pageSpacing = 16.dp
                                        ) { index ->
                                            if (index < 5 && index < anniversaries.size) {
                                                // 실제 데이터 표시 (0~4번째)
                                                AnniversaryCard(
                                                    item = anniversaries[index],
                                                    modifier = Modifier.fillMaxWidth()
                                                )
                                            } else if (index == 5 && anniversaries.size > 5) {
                                                // 더보기 카드 표시 (5번째)
                                                MoreCard(
                                                    totalCount = anniversaries.size,
                                                    cardType = "기념일",
                                                    onClick = {
                                                        showAllAnniversariesModal = true
                                                    },
                                                    modifier = Modifier.fillMaxWidth()
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            // When Date Section
                            item {
                                SectionCard(
                                    title = "When Date?", onAddPressed = {
                                        showCreatePlanModal = true
                                    }) {
                                    if (plans.isEmpty()) {
                                        EmptyContent("데이트")
                                    } else {
                                        // 좌우 스크롤로 페이지 전환
                                        HorizontalPager(
                                            state = planPagerState,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(160.dp),
                                            pageSpacing = 16.dp
                                        ) { index ->
                                            if (index < 5 && index < plans.size) {
                                                // 실제 데이터 표시 (0~4번째)
                                                PlanCard(
                                                    plan = plans[index],
                                                    modifier = Modifier.fillMaxWidth()
                                                )
                                            } else if (index == 5 && plans.size > 5) {
                                                // 더보기 카드 표시 (5번째)
                                                MoreCard(
                                                    totalCount = plans.size,
                                                    cardType = "약속",
                                                    onClick = {
                                                        showAllPlansModal = true
                                                    },
                                                    modifier = Modifier.fillMaxWidth()
                                                )
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
            CreateAnniversaryModal(onCreate = { title, date ->
                Log.d(
                    "HomeScreen", "onCreateAnniversary called with title: $title, date: $date"
                )
                anniversaryViewModel.createAnniversary(title, date)
                showCreateAnniversaryModal = false
            }, onDismiss = { showCreateAnniversaryModal = false })
        }

        if (showCreatePlanModal) {
            CreatePlanModal(onCreate = { topic, location, dateTime ->
                Log.d(
                    "HomeScreen",
                    "onCreatePlan called with title: $topic, location : $location,dateTime: $dateTime"
                )
                planViewModel.createPlan(topic, location, time = dateTime)
                showCreatePlanModal = false
            }, onDismiss = { showCreatePlanModal = false })
        }

        if (showAllAnniversariesModal) {
            ViewAllAnniversariesModal(
                anniversaries = anniversaries,
                onDismiss = {
                    showAllAnniversariesModal = false
                })
        }

        if (showAllPlansModal) {
            ViewAllPlansModal(
                plans = plans,
                onDismiss = { showAllPlansModal = false }
            )
        }
    }
}