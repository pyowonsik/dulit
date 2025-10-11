package com.example.dulit.feature.calendar.presentation.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.dulit.core.ui.theme.Amber600
import com.example.dulit.core.ui.theme.DulitTheme
import com.example.dulit.feature.calendar.domain.model.Calendar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailCalendar(
    calendar: Calendar,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
) {
    DulitTheme {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
        ) {
            // 이미지 섹션
            if (calendar.hasPhotos) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(16.dp)  // 👈 상하좌우 여백 추가
                ) {
                    val pagerState = rememberPagerState(pageCount = { calendar.filePaths.size })
                    
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize()
                    ) { page ->
                        AsyncImage(
                            model = calendar.filePaths[page],
                            contentDescription = "사진 ${page + 1}",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(16.dp)),  // 👈 모서리 둥글게
                            contentScale = ContentScale.Crop
                        )
                    }
                    
                    // 뒤로가기 버튼
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .padding(8.dp)  // 👈 여백 조정 (이미지 여백과 별도)
                            .size(40.dp)
                            .background(
                                color = Color.Black.copy(alpha = 0.5f),
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "뒤로가기",
                            tint = Color.White
                        )
                    }
                    
                    // 페이지 인디케이터
                    if (calendar.photoCount > 1) {
                        Row(
                            Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 8.dp),  // 👈 여백 조정
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            repeat(calendar.photoCount) { index ->
                                Box(
                                    modifier = Modifier
                                        .size(if (pagerState.currentPage == index) 24.dp else 8.dp, 8.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(
                                            if (pagerState.currentPage == index)
                                                Amber600
                                            else
                                                Color.White.copy(alpha = 0.5f)
                                        )
                                )
                            }
                        }
                    }
                }
            } else {
                // 사진 없을 때 헤더
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.surface,
                    shadowElevation = 2.dp
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "뒤로가기",
                                tint = MaterialTheme.colorScheme.secondary
                            )
                        }
                        Text(
                            text = "데이트 기록",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
            
            // 내용 섹션
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                // 제목
                Text(
                    text = calendar.title,
                    style = MaterialTheme.typography.titleLarge.copy(  // 👈 크기 작게
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp  // 👈 28sp → 20sp
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // 구분선
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                )
                
                Spacer(modifier = Modifier.height(16.dp))  // 👈 간격 조정
                
                // 설명
                Text(
                    text = calendar.description,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        lineHeight = 28.sp
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
                
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

// 🎨 Preview용 예제 데이터
@Composable
fun DetailCalendarPreview() {
    val exampleCalendar = Calendar(
        id = 1,
        title = "첫 만남 💕",
        description = "강남역에서 처음 만났던 날! 떨리고 설레었던 그 순간을 잊을 수 없어요. 카페에서 오랜 시간 이야기를 나누고, 함께 걸었던 거리가 아직도 생생해요. 앞으로도 이런 추억을 많이 만들어가고 싶어요 ❤️",
        date = "2025-02-14T00:00:00Z",  // 👈 ISO 형식
        filePaths = listOf(
            "https://picsum.photos/800/600?random=1",
            "https://picsum.photos/800/600?random=2",
            "https://picsum.photos/800/600?random=3"
        )
    )
    
    DetailCalendar(
        calendar = exampleCalendar,
        onBackClick = {}
    )
}

// 사진 없는 버전 Preview
@Composable
fun DetailCalendarNoPhotosPreview() {
    val exampleCalendar = Calendar(
        id = 2,
        title = "영화 관람",
        description = "CGV에서 영화 보고 왔어요! 재밌는 영화였고 같이 보니까 더 좋았어요 🎬",
        date = "2025-03-15T00:00:00Z",  // 👈 ISO 형식
        filePaths = emptyList()
    )
    
    DetailCalendar(
        calendar = exampleCalendar,
        onBackClick = {}
    )
}
