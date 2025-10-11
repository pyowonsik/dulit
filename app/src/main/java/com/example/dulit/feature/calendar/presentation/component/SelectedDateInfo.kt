package com.example.dulit.feature.calendar.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.dulit.core.ui.theme.Amber600
import java.time.LocalDate

@Composable
fun SelectedDateInfo(
    selectedDate: LocalDate,
    modifier: Modifier = Modifier,
    onAddCalendar: (String, String, List<android.net.Uri>) -> Unit = { _, _, _ -> }
) {
    // ⭐ 모달 표시 상태
    var showModal by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
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
        
        // ⭐ 버튼 클릭 시 모달 열기
        IconButton(onClick = { showModal = true }) {
            Icon(
                Icons.Default.AddCircleOutline,
                contentDescription = "기록 추가",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(32.dp)
            )
        }
    }

    // ⭐ 모달 표시
    if (showModal) {
        CreateCalendarModal(
            selectedDate = selectedDate,
            onDismiss = { 
                showModal = false
            },
            onConfirm = { title, description, images ->
                onAddCalendar(title, description, images)
                showModal = false
            }
        )
    }
}

