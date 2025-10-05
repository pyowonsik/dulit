package com.example.dulit.feature.calendar.presentaion

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dulit.core.ui.theme.Amber600
import com.example.dulit.core.ui.theme.DulitTheme
import com.example.dulit.core.ui.theme.Orange500
import com.example.dulit.core.ui.theme.customColorScheme
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.*
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarScreen() {
    // [수정] DulitTheme으로 감싸서 디자인 시스템 적용
    DulitTheme {
        var selectedDate by remember { mutableStateOf(LocalDate.now()) }
        val currentMonth = remember { YearMonth.now() }
        val startMonth = remember { currentMonth.minusMonths(100) }
        val endMonth = remember { currentMonth.plusMonths(100) }
        val daysOfWeek = remember { daysOfWeek() }
        val coroutineScope = rememberCoroutineScope()

        val state = rememberCalendarState(
            startMonth = startMonth,
            endMonth = endMonth,
            firstVisibleMonth = currentMonth,
            firstDayOfWeek = daysOfWeek.first()
        )

        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    // [수정] 커스텀 그라데이션 배경 적용
                    .background(brush = MaterialTheme.customColorScheme.gradientBackground)
            ) {
                Text(
                    text = "데이트 기록",
                    // [수정] 테마 스타일 및 색상 적용
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                Card(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    CalendarHeader(
                        currentMonth = state.firstVisibleMonth.yearMonth,
                        onPrevClicked = {
                            coroutineScope.launch {
                                state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.minusMonths(1))
                            }
                        },
                        onNextClicked = {
                            coroutineScope.launch {
                                state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.plusMonths(1))
                            }
                        }
                    )
                    HorizontalCalendar(
                        state = state,
                        dayContent = { day ->
                            Day(day, isSelected = selectedDate == day.date) { clickedDay ->
                                selectedDate = clickedDay.date
                            }
                        },
                        monthHeader = {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                for (dayOfWeek in daysOfWeek) {
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        textAlign = TextAlign.Center,
                                        text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN),
                                        // [수정] 테마 스타일 및 색상 적용
                                        style = MaterialTheme.typography.labelLarge,
                                        color = if (dayOfWeek == DayOfWeek.SUNDAY || dayOfWeek == DayOfWeek.SATURDAY) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                }
                            }
                        }
                    )
                }

                SelectedDateInfo(selectedDate)
            }
        }
    }
}

@Composable
private fun CalendarHeader(
    currentMonth: YearMonth,
    onPrevClicked: () -> Unit,
    onNextClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = onPrevClicked) {
            // [수정] 기존 Icons.Default 아이콘으로 복원
            Icon(Icons.Default.ChevronLeft, contentDescription = "이전 달", tint = MaterialTheme.colorScheme.secondary)
        }
        Text(
            text = "${currentMonth.year}년 ${currentMonth.month.value}월",
            // [수정] 테마 스타일 및 색상 적용
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp),
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onNextClicked) {
            // [수정] 기존 Icons.Default 아이콘으로 복원
            Icon(Icons.Default.ChevronRight, contentDescription = "다음 달", tint = MaterialTheme.colorScheme.secondary)
        }
    }
}

@Composable
private fun Day(day: CalendarDay, isSelected: Boolean, onClick: (CalendarDay) -> Unit) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(2.dp)
            .clip(CircleShape)
            .background(
                color = when {
                    isSelected -> MaterialTheme.colorScheme.primary
                    day.date == LocalDate.now() -> Orange500 // '오늘' 표시는 고유 색상 유지
                    else -> Color.Transparent
                }
            )
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = { onClick(day) }
            ),
        contentAlignment = Alignment.Center
    ) {
        val textColor = when {
            isSelected || day.date == LocalDate.now() -> MaterialTheme.colorScheme.onPrimary
            day.date.dayOfWeek == DayOfWeek.SUNDAY || day.date.dayOfWeek == DayOfWeek.SATURDAY -> MaterialTheme.colorScheme.secondary
            day.position != DayPosition.MonthDate -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
            else -> MaterialTheme.colorScheme.onSurface
        }
        Text(
            text = day.date.dayOfMonth.toString(),
            color = textColor,
            // [수정] 테마 스타일 적용
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isSelected || day.date == LocalDate.now()) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
private fun SelectedDateInfo(selectedDate: LocalDate) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .background(Amber600, RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "${selectedDate.year}년 ${selectedDate.monthValue}월 ${selectedDate.dayOfMonth}일",
                    color = Color.White,
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "의 우리의 기록",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        IconButton(onClick = { /* 새 기록 추가 로직 */ }) {
            Icon(
                Icons.Default.AddCircleOutline,
                contentDescription = "기록 추가",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}