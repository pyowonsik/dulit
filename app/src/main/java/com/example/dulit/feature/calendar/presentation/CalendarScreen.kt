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
import com.example.dulit.feature.calendar.presentation.component.CalendarCard
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
                    }
                )

                SelectedDateInfo(selectedDate)
            }
        }
    }
}
