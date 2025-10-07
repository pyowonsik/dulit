    package com.example.dulit.feature.chat.presentaion

    import androidx.compose.foundation.background
    import androidx.compose.foundation.border
    import androidx.compose.foundation.clickable
    import androidx.compose.foundation.layout.*
    import androidx.compose.foundation.lazy.LazyColumn
    import androidx.compose.foundation.lazy.items
    import androidx.compose.foundation.lazy.rememberLazyListState
    import androidx.compose.foundation.shape.CircleShape
    import androidx.compose.foundation.shape.CornerSize
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.foundation.text.BasicTextField
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.automirrored.filled.Send // [수정] AutoMirrored 아이콘 사용
    import androidx.compose.material.icons.filled.Add
    import androidx.compose.material.icons.filled.MoreVert
    import androidx.compose.material3.*
    import androidx.compose.runtime.*
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.draw.clip
    import androidx.compose.ui.draw.shadow
    import androidx.compose.ui.graphics.Brush
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.graphics.SolidColor
    import androidx.compose.ui.graphics.vector.ImageVector
    import androidx.compose.ui.platform.LocalConfiguration
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import com.example.dulit.core.ui.theme.Amber900
    import com.example.dulit.core.ui.theme.DulitTheme
    import com.example.dulit.core.ui.theme.customColorScheme
    import kotlinx.coroutines.launch

    // Data classes remain the same...
    data class ChatMessage(
        val text: String,
        val isMe: Boolean,
        val time: String,
    )

    private val sampleMessages = listOf(
        ChatMessage("안녕하세요! 오늘 뭐하고 있어요?", isMe = false, time = "오전 10:30"),
        ChatMessage("안녕! 나 지금 카페에서 공부하고 있어 ☕", isMe = true, time = "오전 10:32"),
        // ...
    )

    @Composable
    fun ChatScreen() {
        // [수정] DulitTheme으로 감싸서 디자인 시스템 적용
        DulitTheme {
            var messages by remember { mutableStateOf(sampleMessages) }
            val listState = rememberLazyListState()
            val coroutineScope = rememberCoroutineScope()

            Surface(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        // [수정] 커스텀 그라데이션 배경 적용
                        .background(brush = MaterialTheme.customColorScheme.gradientBackground)
                ) {
                    ChatHeader()
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        item { DateDivider("오늘") }
                        items(messages) { message -> MessageItem(message) }
                    }
                    MessageInput { text ->
                        messages = messages + ChatMessage(text, isMe = true, time = "지금")
                        coroutineScope.launch { listState.animateScrollToItem(messages.size) }
                    }
                }
            }
        }
    }

    @Composable
    private fun ChatHeader() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 4.dp, spotColor = MaterialTheme.colorScheme.outlineVariant)
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                // Icon(...) // 뒤로가기 아이콘
            }
            Spacer(modifier = Modifier.width(12.dp))
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("💑", style = MaterialTheme.typography.bodyLarge)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "사랑하는 우리 💕",
                    // [수정] 테마 스타일 및 색상 적용
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(MaterialTheme.customColorScheme.success, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "온라인",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    Icons.Default.MoreVert,
                    contentDescription = "더보기",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }

    @Composable
    private fun DateDivider(date: String) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Divider(modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.outline)
            Box(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = date,
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.W500),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            Divider(modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.outline)
        }
    }

    @Composable
    private fun MessageItem(message: ChatMessage) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
            horizontalArrangement = if (message.isMe) Arrangement.End else Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {
            if (!message.isMe) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
                        .border(1.5.dp, MaterialTheme.colorScheme.primaryContainer.copy(alpha=0.5f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("💑", style = MaterialTheme.typography.bodySmall)
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
            if (message.isMe) {
                Text(
                    text = message.time,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
            Box(
                modifier = Modifier
                    .widthIn(max = LocalConfiguration.current.screenWidthDp.dp * 0.65f)
                    .shadow(1.dp, RoundedCornerShape(18.dp), spotColor = MaterialTheme.colorScheme.surfaceVariant)
                    .background(
                        color = if (message.isMe) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(18.dp).copy(
                            bottomStart = if (message.isMe) RoundedCornerShape(18.dp).bottomStart else CornerSize(0.dp),
                            bottomEnd = if (message.isMe) CornerSize(0.dp) else RoundedCornerShape(18.dp).bottomEnd
                        )
                    )
                    .border(
                        width = if (message.isMe) 0.dp else 1.dp,
                        color = if (message.isMe) Color.Transparent else MaterialTheme.colorScheme.outlineVariant,
                        shape = RoundedCornerShape(18.dp).copy(
                            bottomStart = if (message.isMe) RoundedCornerShape(18.dp).bottomStart else CornerSize(0.dp),
                            bottomEnd = if (message.isMe) CornerSize(0.dp) else RoundedCornerShape(18.dp).bottomEnd
                        )
                    )
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                Text(
                    text = message.text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (message.isMe) Amber900 else MaterialTheme.colorScheme.onSurface
                )
            }
            if (!message.isMe) {
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = message.time,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }

    @Composable
    private fun MessageInput(onSend: (String) -> Unit) {
        var text by remember { mutableStateOf("") }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 4.dp, spotColor = MaterialTheme.colorScheme.outlineVariant)
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RoundIconButton(icon = Icons.Default.Add, onClick = { /*TODO*/ })
            Spacer(modifier = Modifier.width(8.dp))
            BasicTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(24.dp))
                    .border(1.dp, MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(24.dp))
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.secondary),
                decorationBox = { innerTextField ->
                    if (text.isEmpty()) {
                        Text(
                            "메시지를 입력하세요...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    }
                    innerTextField()
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .shadow(elevation = 8.dp, shape = CircleShape, spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.primaryContainer)
                        ),
                        shape = CircleShape
                    )
                    .clip(CircleShape)
                    .clickable {
                        if (text.isNotBlank()) {
                            onSend(text)
                            text = ""
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    // [수정] AutoMirrored 아이콘으로 변경
                    Icons.AutoMirrored.Filled.Send,
                    contentDescription = "전송",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }

    @Composable
    private fun RoundIconButton(icon: ImageVector, onClick: () -> Unit) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(MaterialTheme.colorScheme.secondaryContainer, CircleShape)
                .clip(CircleShape)
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(24.dp)
            )
        }
    }
