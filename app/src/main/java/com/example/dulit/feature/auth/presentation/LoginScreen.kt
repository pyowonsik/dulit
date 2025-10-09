// feature/auth/presentation/LoginScreen.kt
package com.example.dulit.feature.auth.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.dulit.R
import com.example.dulit.core.ui.theme.DulitNavy
import com.example.dulit.core.ui.theme.DulitNavy50
import com.example.dulit.feature.chat.presentation.ChatViewModel
import com.example.dulit.feature.user.presentation.ConnectBottomSheet  // 👈 추가 예정
import com.example.dulit.feature.user.presentation.ConnectCoupleState
import com.example.dulit.feature.user.presentation.ConnectCoupleViewModel
import com.example.dulit.feature.user.presentation.ConnectSocketViewModel
import com.example.dulit.navigation.Route
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

// feature/auth/presentation/LoginScreen.kt
@Composable
fun LoginScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel(),
    connectCoupleViewModel: ConnectCoupleViewModel = hiltViewModel(),  // 👈 추가
    connectSocketViewModel : ConnectSocketViewModel = hiltViewModel()
//    chatViewModel : ChatViewModel = hiltViewModel()
    ) {
    val context = LocalContext.current
    val loginState by loginViewModel.loginState.collectAsState()
    val connectState by connectCoupleViewModel.connectState.collectAsState()  // 👈 상태 관찰

    var showConnectModal by remember { mutableStateOf(false) }
    var mySocialId by remember { mutableStateOf("") }

    // 로그인 상태 관찰
    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginState.AlreadyConnected -> {
                val response = (loginState as LoginState.AlreadyConnected).response
                Log.d("LoginScreen", "커플 연결됨: ${response.user.name}, isCouple: ${response.isCouple}")
                navController.navigate(Route.Root.route) {
                    popUpTo(Route.Login.route) { inclusive = true }
                }
            }


            is LoginState.NeedConnection -> {
                val response = (loginState as LoginState.NeedConnection).response
                Log.d("LoginScreen", "커플 미연결: ${response.user.name}, isCouple: ${response.isCouple}")
                mySocialId = response.user.socialId.toString()  // 👈 여기 수정!
                showConnectModal = true
            }

            is LoginState.Error -> {
                Toast.makeText(context, "로그인 실패", Toast.LENGTH_SHORT).show()
            }

            else -> {}
        }
    }

    // 👇 커플 연결 상태 관찰
    LaunchedEffect(connectState) {
        when (connectState) {
            is ConnectCoupleState.Success -> {
                Toast.makeText(
                    context,
                    "연결 요청 완료! 알림을 기다려주세요",
                    Toast.LENGTH_SHORT
                ).show()
            }
            is ConnectCoupleState.Error -> {
                val message = (connectState as ConnectCoupleState.Error).message
                Toast.makeText(context, "연결 실패: $message", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    // 카카오 로그인 콜백
    val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e("KakaoLogin", "카카오 로그인 실패", error)
        } else if (token != null) {
            loginViewModel.kakaoLogin(token.accessToken)
        }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "둘잇",
                        style = MaterialTheme.typography.displayLarge,
                        color = DulitNavy
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "두 사람을 이어주는 특별한 공간",
                        style = MaterialTheme.typography.bodyLarge,
                        color = DulitNavy50
                    )
                }

                Column(
                    modifier = Modifier.padding(all = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.kakao_login),
                        contentDescription = "카카오 로그인",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(enabled = loginState !is LoginState.Loading) {
                                if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                                    UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                                        if (error != null) {
                                            if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                                                return@loginWithKakaoTalk
                                            }
                                            UserApiClient.instance.loginWithKakaoAccount(
                                                context,
                                                callback = kakaoCallback
                                            )
                                        } else {
                                            kakaoCallback(token, null)
                                        }
                                    }
                                } else {
                                    UserApiClient.instance.loginWithKakaoAccount(
                                        context,
                                        callback = kakaoCallback
                                    )
                                }
                            }
                    )
                }
            }

            if (loginState is LoginState.Loading || connectState is ConnectCoupleState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }

    // 👇 Connect 모달 - 콜백으로 ViewModel 호출
    if (showConnectModal) {
        ConnectBottomSheet(
            mySocialId = mySocialId,
            onDismiss = {
                if (connectState !is ConnectCoupleState.Loading) {
                    connectSocketViewModel.disconnectSocket()  // 👈 추가!
                    showConnectModal = false
                    loginViewModel.resetState()
                    connectCoupleViewModel.resetState()
                }
            },
            onConnect = { partnerCode ->
                Log.d("LoginScreen", "커플 연결 콜백 → ViewModel 호출")
                connectCoupleViewModel.connectCouple(partnerCode)  // 👈 ViewModel 호출
            },
            onMatchedNotification = {
                Log.i("LoginScreen", "📩 매칭 알림 수신 → Home 이동")
                showConnectModal = false
                connectSocketViewModel.disconnectSocket()  // 1️⃣ 알림 소켓 해제
                // chatViewModel.connectChatSocket()          // 2️⃣ 채팅 소켓 연결
                navController.navigate(Route.Root.route) {
                    popUpTo(Route.Login.route) { inclusive = true }
                }
            }
        )
    }
}