package com.example.dulit.feature.calendar.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.dulit.core.ui.theme.DulitTheme
import com.example.dulit.core.ui.theme.customColorScheme
import com.example.dulit.feature.calendar.domain.model.Calendar
import com.example.dulit.feature.calendar.presentation.component.CalendarCard
import com.example.dulit.feature.calendar.presentation.component.DetailCalendar
import com.example.dulit.feature.calendar.presentation.component.SelectedDateInfo
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.*
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun CalendarScreen() {
    DulitTheme {
        var selectedDate by remember { mutableStateOf(LocalDate.now()) }
        val currentMonth = remember { YearMonth.now() }
        val startMonth = remember { currentMonth.minusMonths(100) }
        val endMonth = remember { currentMonth.plusMonths(100) }
        val daysOfWeek = remember { daysOfWeek() }

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
                    .background(brush = MaterialTheme.customColorScheme.gradientBackground)
            ) {
                Text(
                    text = "데이트 기록",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                // ⭐ CalendarCard 분리
                CalendarCard(
                    state = state,
                    selectedDate = selectedDate,
                    daysOfWeek = daysOfWeek,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onDateSelected = { date ->
                        selectedDate = date
                    })

                SelectedDateInfo(selectedDate)
                DetailCalendar(
                    Calendar(
                        id = 1,
                        title = "첫 만남 💕",
                        description = "강남역에서 처음 만났던 날! 떨리고 설레었던 그 순간을 잊을 수 없어요. 카페에서 오랜 시간 이야기를 나누고, 함께 걸었던 거리가 아직도 생생해요. 앞으로도 이런 추억을 많이 만들어가고 싶어요 ❤️",
                        date = "2025-02-14",
                        filePaths = listOf(
                            "https://picsum.photos/800/600?random=1",
                            "https://picsum.photos/800/600?random=2",
                            "https://picsum.photos/800/600?random=3"
                        )
                    )
                )
            }
        }
    }
}
