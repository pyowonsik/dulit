// feature/couple/presentation/ConnectBottomSheet.kt
package com.example.dulit.feature.couple.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dulit.core.ui.theme.DulitNavy
import com.example.dulit.feature.couple.domain.repository.MatchingSocketState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConnectBottomSheet(
    mySocialId: String,
    matchingViewModel: CoupleMatchingViewModel = hiltViewModel(),  // 👈 변경
    onDismiss: () -> Unit,
    onConnect: (String) -> Unit,
    onMatchedNotification: () -> Unit
) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    var partnerCode by remember { mutableStateOf("") }
    var showConfirmDialog by remember { mutableStateOf(false) }

    // 👇 매칭 소켓 상태 관찰
    val matchingState by matchingViewModel.matchingState.collectAsState()

    // 👇 모달 열릴 때 소켓 연결
    LaunchedEffect(Unit) {
        Log.d("ConnectBottomSheet", "모달 열림 - 소켓 연결 시작")
        matchingViewModel.connectSocket(mySocialId)
    }

    // 👇 매칭 알림 감지
    LaunchedEffect(matchingState) {
        if (matchingState is MatchingSocketState.Matched) {
            val message = (matchingState as MatchingSocketState.Matched).message
            Log.i("ConnectBottomSheet", "📩 매칭 완료: $message")
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            onMatchedNotification()
        }
    }

    // 👇 모달 닫힐 때 소켓 해제
    DisposableEffect(Unit) {
        onDispose {
            Log.d("ConnectBottomSheet", "모달 닫힘 - 소켓 해제")
            matchingViewModel.disconnectSocket()
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // 타이틀
            Text(
                text = "커플 연결을 해주세요 💙",
                style = MaterialTheme.typography.headlineSmall,
                color = DulitNavy
            )

            // 👇 연결 상태 표시
            when (matchingState) {
                is MatchingSocketState.Connected -> {
                    Text(
                        "✅ 알림 대기 중",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                is MatchingSocketState.Error -> {
                    Text(
                        "❌ 연결 실패",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                is MatchingSocketState.Idle -> {
                    Text(
                        "🔌 연결 중...",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                else -> {}
            }

            HorizontalDivider()

            // 내 코드 섹션
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "당신의 코드",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = mySocialId,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.weight(1f)
                        )

                        IconButton(
                            onClick = {
                                clipboardManager.setText(AnnotatedString(mySocialId))
                                Toast.makeText(context, "코드가 복사되었습니다!", Toast.LENGTH_SHORT).show()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "복사하기"
                            )
                        }
                    }
                }

                Button(
                    onClick = {
                        clipboardManager.setText(AnnotatedString(mySocialId))
                        Toast.makeText(context, "코드가 복사되었습니다! 상대방에게 공유하세요", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = DulitNavy)
                ) {
                    Text("복사하기")
                }
            }

            HorizontalDivider()

            // 상대방 코드 입력 섹션
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "상대방 코드를 입력해주세요",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                OutlinedTextField(
                    value = partnerCode,
                    onValueChange = { partnerCode = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("상대방 코드 입력") },
                    singleLine = true
                )

                Button(
                    onClick = {
                        if (partnerCode.isNotBlank()) {
                            showConfirmDialog = true
                        } else {
                            Toast.makeText(context, "상대방 코드를 입력하세요", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = DulitNavy),
                    enabled = partnerCode.isNotBlank()
                ) {
                    Text("연결하기")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    // 연결 확인 다이얼로그
    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = { Text("커플 연결") },
            text = { Text("이 코드로 연결하시겠습니까?\n\n$partnerCode") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showConfirmDialog = false
                        onConnect(partnerCode)
                    }
                ) {
                    Text("확인", color = DulitNavy)
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmDialog = false }) {
                    Text("취소")
                }
            }
        )
    }
}