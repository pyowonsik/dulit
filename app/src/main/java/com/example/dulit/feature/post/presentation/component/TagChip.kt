package com.example.dulit.feature.post.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * 태그 칩 (해시태그)
 * 
 * 사용 예제:
 * ```kotlin
 * Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
 *     TagChip(label = "남산타워")
 *     TagChip(label = "데이트코스")
 *     TagChip(label = "야경")
 * }
 * ```
 */
@Composable
fun TagChip(
    label: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Text(
            text = "#$label",
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

/**
 * 태그 칩 목록 (여러 개)
 */
@Composable
fun TagChips(
    tags: List<String>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tags.forEach { tag ->
            TagChip(label = tag)
        }
    }
}

