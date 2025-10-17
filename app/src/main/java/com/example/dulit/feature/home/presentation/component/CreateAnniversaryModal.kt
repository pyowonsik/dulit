package com.example.dulit.feature.home.presentation.component

import ConfirmButton
import android.app.DatePickerDialog
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.dulit.core.ui.component.CancelButton
import com.example.dulit.core.ui.theme.customColorScheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * 기념일 생성 모달 - 재디자인 버전
 *
 * @param onDismiss 모달 닫기 콜백
 * @param onCreate 생성 버튼 클릭 시 콜백 (title, date)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAnniversaryModal(
    onDismiss: () -> Unit, onCreate: (title: String, date: String) -> Unit
) {
    val context = LocalContext.current

    // 입력 상태
    var title by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var titleError by remember { mutableStateOf(false) }
    var dateError by remember { mutableStateOf(false) }

    // 애니메이션 상태
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val shimmerAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f, targetValue = 0.7f, animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing), repeatMode = RepeatMode.Reverse
        ), label = "shimmer"
    )

    // DatePicker 상태
    val calendar = Calendar.getInstance()

    Dialog(
        onDismissRequest = onDismiss, properties = DialogProperties(
            usePlatformDefaultWidth = false, dismissOnBackPress = true, dismissOnClickOutside = true
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 32.dp,
                        shape = RoundedCornerShape(32.dp),
                        ambientColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                        spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
                    ), shape = RoundedCornerShape(32.dp), colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f),
                                    MaterialTheme.colorScheme.surface,
                                    MaterialTheme.colorScheme.surface
                                )
                            )
                        )
                        .padding(32.dp)
                ) {
                    // 헤더 섹션
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // 아이콘 배지
                            Box(
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(
                                        brush = Brush.linearGradient(
                                            colors = listOf(
                                                MaterialTheme.colorScheme.primary.copy(alpha = shimmerAlpha),
                                                MaterialTheme.colorScheme.tertiary.copy(alpha = shimmerAlpha)
                                            )
                                        )
                                    ), contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(32.dp)
                                )
                            }

                            // 타이틀
                            Text(
                                text = "특별한 날을\n기록하세요",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = MaterialTheme.typography.headlineMedium.lineHeight
                            )

                            Text(
                                text = "소중한 기념일을 추가해보세요 💕",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        // 닫기 버튼
                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier
                                .size(40.dp)
                                .offset(x = 8.dp, y = (-8).dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "닫기",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // 기념일 이름 입력
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "기념일 이름",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        OutlinedTextField(
                            value = title,
                            onValueChange = {
                                title = it
                                titleError = false
                            },
                            placeholder = {
                                Text(
                                    "예: 우리 만난지, 첫 키스, 결혼기념일",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            isError = titleError,
                            supportingText = if (titleError) {
                                {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Text(
                                            "⚠️", style = MaterialTheme.typography.bodySmall
                                        )
                                        Text(
                                            "기념일 이름을 입력해주세요",
                                            color = MaterialTheme.colorScheme.error,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }
                            } else null,
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                focusedLabelColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                                focusedContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(
                                    alpha = 0.08f
                                ),
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(
                                    alpha = 0.3f
                                )
                            ),
                            textStyle = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Medium
                            ))
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // 날짜 선택
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "날짜",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Surface(
                            onClick = {
                                val year = selectedDate?.year ?: calendar.get(Calendar.YEAR)
                                val month = selectedDate?.monthValue?.minus(1) ?: calendar.get(
                                    Calendar.MONTH
                                )
                                val day =
                                    selectedDate?.dayOfMonth ?: calendar.get(Calendar.DAY_OF_MONTH)

                                DatePickerDialog(
                                    context, { _, selectedYear, selectedMonth, selectedDay ->
                                        selectedDate = LocalDate.of(
                                            selectedYear, selectedMonth + 1, selectedDay
                                        )
                                        dateError = false
                                    }, year, month, day
                                ).show()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(64.dp),
                            shape = RoundedCornerShape(16.dp),
                            color = if (dateError) {
                                MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.15f)
                            } else if (selectedDate != null) {
                                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)
                            } else {
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                            },
                            border = if (dateError) {
                                ButtonDefaults.outlinedButtonBorder.copy(
                                    width = 2.dp,
                                    brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.error)
                                )
                            } else if (selectedDate != null) {
                                ButtonDefaults.outlinedButtonBorder.copy(
                                    width = 1.5.dp, brush = androidx.compose.ui.graphics.SolidColor(
                                        MaterialTheme.colorScheme.primary.copy(
                                            alpha = 0.5f
                                        )
                                    )
                                )
                            } else {
                                ButtonDefaults.outlinedButtonBorder.copy(
                                    width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(
                                        MaterialTheme.colorScheme.outline.copy(
                                            alpha = 0.3f
                                        )
                                    )
                                )
                            }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 20.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(text = selectedDate?.let {
                                        it.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"))
                                    } ?: "날짜를 선택해주세요",
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = if (selectedDate != null) FontWeight.SemiBold else FontWeight.Normal,
                                        color = if (selectedDate != null) {
                                            MaterialTheme.colorScheme.onSurface
                                        } else {
                                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                                        })

                                    if (selectedDate != null) {
                                        Text(
                                            text = selectedDate!!.dayOfWeek.getDisplayName(
                                                java.time.format.TextStyle.FULL, Locale.KOREAN
                                            ),
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }

                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                                    modifier = Modifier.size(44.dp)
                                ) {
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.CalendarToday,
                                            contentDescription = "날짜 선택",
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(22.dp)
                                        )
                                    }
                                }
                            }
                        }

                        AnimatedVisibility(
                            visible = dateError,
                            enter = fadeIn() + expandVertically(),
                            exit = fadeOut() + shrinkVertically()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                            ) {
                                Text(
                                    "⚠️", style = MaterialTheme.typography.bodySmall
                                )
                                Text(
                                    text = "날짜를 선택해주세요",
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(36.dp))

                    // 버튼 그룹
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // 취소 버튼
                        CancelButton(
                            onCancel = onDismiss, modifier = Modifier.weight(1f)
                        )

                        // 생성 버튼
                        ConfirmButton(
                            onClick = {
                                // 유효성 검사
                                var hasError = false

                                if (title.isBlank()) {
                                    titleError = true
                                    hasError = true
                                }

                                if (selectedDate == null) {
                                    dateError = true
                                    hasError = true
                                }

                                if (!hasError) {
                                    // ISO 8601 형식으로 변환
                                    val formattedDate = selectedDate!!.format(
                                        DateTimeFormatter.ISO_LOCAL_DATE
                                    )
                                    onCreate(title.trim(), formattedDate)
                                }
                            }, modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}