package com.example.dulit.feature.home.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dulit.core.ui.theme.Amber200
import com.example.dulit.feature.home.domain.model.Anniversary


@Composable
fun AnniversaryCard(
    item: Anniversary, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
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
                text = "❤️",
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 28.sp)
            )
            Column(
                modifier = Modifier.align(Alignment.CenterEnd), horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = item.date,
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
