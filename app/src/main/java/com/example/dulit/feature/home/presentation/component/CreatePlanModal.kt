package com.example.dulit.feature.home.presentation.component

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.example.dulit.core.ui.theme.customColorScheme
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * 데이트 약속 생성 모달
 *
 * @param onDismiss 모달 닫기 콜백
 * @param onCreate 생성 버튼 클릭 시 콜백 (topic, location, dateTime)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePlanModal(
    onDismiss: () -> Unit,
    onCreate: (topic: String, location: String, dateTime: String) -> Unit
) {
    val context = LocalContext.current

    // 입력 상태
    var topic by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedTime by remember { mutableStateOf<LocalTime?>(null) }
    var topicError by remember { mutableStateOf(false) }
    var locationError by remember { mutableStateOf(false) }
    var dateTimeError by remember { mutableStateOf(false) }

    // 애니메이션 상태
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val shimmerAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shimmer"
    )

    // DatePicker 상태
    val calendar = Calendar.getInstance()

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = true
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
                    ),
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.15f),
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
                                                MaterialTheme.colorScheme.tertiary.copy(alpha = shimmerAlpha),
                                                MaterialTheme.colorScheme.primary.copy(alpha = shimmerAlpha)
                                            )
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(32.dp)
                                )
                            }

                            // 타이틀
                            Text(
                                text = "데이트 약속\n만들기",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = MaterialTheme.typography.headlineMedium.lineHeight
                            )

                            Text(
                                text = "특별한 데이트를 계획해보세요 💑",
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

                    // 약속 주제 입력
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "무엇을 할까요?",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        OutlinedTextField(
                            value = topic,
                            onValueChange = {
                                topic = it
                                topicError = false
                            },
                            placeholder = {
                                Text(
                                    "예: 영화 보기, 맛집 탐방, 산책하기",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.FavoriteBorder,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            isError = topicError,
                            supportingText = if (topicError) {
                                {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Text("⚠️", style = MaterialTheme.typography.bodySmall)
                                        Text(
                                            "약속 주제를 입력해주세요",
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
                                focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.08f),
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                            ),
                            textStyle = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // 장소 입력
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "어디에서 만날까요?",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        OutlinedTextField(
                            value = location,
                            onValueChange = {
                                location = it
                                locationError = false
                            },
                            placeholder = {
                                Text(
                                    "예: 강남역, 홍대입구, 코엑스",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            isError = locationError,
                            supportingText = if (locationError) {
                                {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Text("⚠️", style = MaterialTheme.typography.bodySmall)
                                        Text(
                                            "장소를 입력해주세요",
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
                                focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.08f),
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                            ),
                            textStyle = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // 날짜 & 시간 선택
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "언제 만날까요?",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // 날짜 선택
                            Surface(
                                onClick = {
                                    val year = selectedDate?.year ?: calendar.get(Calendar.YEAR)
                                    val month = selectedDate?.monthValue?.minus(1) ?: calendar.get(Calendar.MONTH)
                                    val day = selectedDate?.dayOfMonth ?: calendar.get(Calendar.DAY_OF_MONTH)

                                    DatePickerDialog(
                                        context,
                                        { _, selectedYear, selectedMonth, selectedDay ->
                                            selectedDate = LocalDate.of(selectedYear, selectedMonth + 1, selectedDay)
                                            dateTimeError = false
                                        },
                                        year,
                                        month,
                                        day
                                    ).show()
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(72.dp),
                                shape = RoundedCornerShape(16.dp),
                                color = if (dateTimeError) {
                                    MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.15f)
                                } else if (selectedDate != null) {
                                    MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.3f)
                                } else {
                                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                },
                                border = if (dateTimeError) {
                                    ButtonDefaults.outlinedButtonBorder.copy(
                                        width = 2.dp,
                                        brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.error)
                                    )
                                } else if (selectedDate != null) {
                                    ButtonDefaults.outlinedButtonBorder.copy(
                                        width = 1.5.dp,
                                        brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f))
                                    )
                                } else {
                                    ButtonDefaults.outlinedButtonBorder.copy(
                                        width = 1.dp,
                                        brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                                    )
                                }
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(16.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CalendarToday,
                                        contentDescription = "날짜",
                                        tint = if (selectedDate != null) {
                                            MaterialTheme.colorScheme.tertiary
                                        } else {
                                            MaterialTheme.colorScheme.onSurfaceVariant
                                        },
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = selectedDate?.format(DateTimeFormatter.ofPattern("MM/dd")) ?: "날짜",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = if (selectedDate != null) FontWeight.Bold else FontWeight.Normal,
                                        color = if (selectedDate != null) {
                                            MaterialTheme.colorScheme.onSurface
                                        } else {
                                            MaterialTheme.colorScheme.onSurfaceVariant
                                        }
                                    )
                                }
                            }

                            // 시간 선택
                            Surface(
                                onClick = {
                                    val hour = selectedTime?.hour ?: calendar.get(Calendar.HOUR_OF_DAY)
                                    val minute = selectedTime?.minute ?: calendar.get(Calendar.MINUTE)

                                    TimePickerDialog(
                                        context,
                                        { _, selectedHour, selectedMinute ->
                                            selectedTime = LocalTime.of(selectedHour, selectedMinute)
                                            dateTimeError = false
                                        },
                                        hour,
                                        minute,
                                        false
                                    ).show()
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(72.dp),
                                shape = RoundedCornerShape(16.dp),
                                color = if (dateTimeError) {
                                    MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.15f)
                                } else if (selectedTime != null) {
                                    MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.3f)
                                } else {
                                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                },
                                border = if (dateTimeError) {
                                    ButtonDefaults.outlinedButtonBorder.copy(
                                        width = 2.dp,
                                        brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.error)
                                    )
                                } else if (selectedTime != null) {
                                    ButtonDefaults.outlinedButtonBorder.copy(
                                        width = 1.5.dp,
                                        brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f))
                                    )
                                } else {
                                    ButtonDefaults.outlinedButtonBorder.copy(
                                        width = 1.dp,
                                        brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                                    )
                                }
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(16.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Schedule,
                                        contentDescription = "시간",
                                        tint = if (selectedTime != null) {
                                            MaterialTheme.colorScheme.tertiary
                                        } else {
                                            MaterialTheme.colorScheme.onSurfaceVariant
                                        },
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = selectedTime?.format(DateTimeFormatter.ofPattern("HH:mm")) ?: "시간",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = if (selectedTime != null) FontWeight.Bold else FontWeight.Normal,
                                        color = if (selectedTime != null) {
                                            MaterialTheme.colorScheme.onSurface
                                        } else {
                                            MaterialTheme.colorScheme.onSurfaceVariant
                                        }
                                    )
                                }
                            }
                        }

                        AnimatedVisibility(
                            visible = dateTimeError,
                            enter = fadeIn() + expandVertically(),
                            exit = fadeOut() + shrinkVertically()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                            ) {
                                Text("⚠️", style = MaterialTheme.typography.bodySmall)
                                Text(
                                    text = "날짜와 시간을 모두 선택해주세요",
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
                        OutlinedButton(
                            onClick = onDismiss,
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.onSurface
                            ),
                            border = ButtonDefaults.outlinedButtonBorder.copy(
                                width = 1.5.dp,
                                brush = androidx.compose.ui.graphics.SolidColor(
                                    MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                                )
                            )
                        ) {
                            Text(
                                text = "취소",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        // 생성 버튼
                        Button(
                            onClick = {
                                // 유효성 검사
                                var hasError = false

                                if (topic.isBlank()) {
                                    topicError = true
                                    hasError = true
                                }

                                if (location.isBlank()) {
                                    locationError = true
                                    hasError = true
                                }

                                if (selectedDate == null || selectedTime == null) {
                                    dateTimeError = true
                                    hasError = true
                                }

                                if (!hasError) {
                                    // LocalDateTime 생성 및 ISO 형식으로 변환
                                    val dateTime = LocalDateTime.of(selectedDate!!, selectedTime!!)
                                    val formattedDateTime = dateTime.format(
                                        DateTimeFormatter.ISO_LOCAL_DATE_TIME
                                    )
                                    onCreate(topic.trim(), location.trim(), formattedDateTime)
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.tertiary,
                                contentColor = MaterialTheme.colorScheme.onTertiary
                            ),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 4.dp,
                                pressedElevation = 8.dp
                            )
                        ) {
                            Text(
                                text = "약속 잡기 💕",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
