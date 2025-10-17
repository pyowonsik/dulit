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
import com.example.dulit.core.ui.theme.DulitTheme
import com.example.dulit.feature.couple.presentation.ConnectBottomSheet
import com.example.dulit.feature.couple.presentation.ConnectCoupleState
import com.example.dulit.feature.couple.presentation.ConnectCoupleViewModel
import com.example.dulit.feature.couple.presentation.CoupleMatchingViewModel
import com.example.dulit.navigation.Route
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

@Composable
fun LoginScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel(),
    connectCoupleViewModel: ConnectCoupleViewModel = hiltViewModel(),
    matchingViewModel: CoupleMatchingViewModel = hiltViewModel()
) {
    DulitTheme {
        val context = LocalContext.current
        val loginState by loginViewModel.loginState.collectAsState()
        val connectState by connectCoupleViewModel.connectState.collectAsState()

        var showConnectModal by remember { mutableStateOf(false) }
        var mySocialId by remember { mutableStateOf("") }

        // 👇 화면 진입 시 자동 로그인 시도
        LaunchedEffect(Unit) {
            Log.d("LoginScreen", "🚀 화면 시작 - 자동 로그인 시도")
            loginViewModel.autoLogin()
        }

        // 로그인 상태 관찰
        LaunchedEffect(loginState) {
            when (loginState) {
                is LoginState.AlreadyConnected -> {
                    val response = (loginState as LoginState.AlreadyConnected).response
                    Log.d(
                        "LoginScreen",
                        "✅ 커플 연결됨: ${response.user.name}, isCouple: ${response.isCouple}"
                    )
                    navController.navigate(Route.Root.route) {
                        popUpTo(Route.Login.route) { inclusive = true }
                    }
                }

                is LoginState.NeedConnection -> {
                    val response = (loginState as LoginState.NeedConnection).response
                    Log.d(
                        "LoginScreen",
                        "❌ 커플 미연결: ${response.user.name}, isCouple: ${response.isCouple}"
                    )
                    mySocialId = response.user.socialId.toString()
                    showConnectModal = true
                }

                is LoginState.Error -> {
                    Toast.makeText(context, "로그인 실패", Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }

        // 커플 연결 상태 관찰
        LaunchedEffect(connectState) {
            when (connectState) {
                is ConnectCoupleState.Success -> {}

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
                // 👇 AlreadyConnected가 아닐 때만 UI 표시 (홈 이동 전까지)
                if (loginState !is LoginState.CheckingAutoLogin && loginState !is LoginState.AlreadyConnected) {
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
                                    .clickable(
                                        // 👇 로딩 중이거나 NeedConnection 상태일 때 클릭 불가
                                        enabled = loginState !is LoginState.Loading && loginState !is LoginState.NeedConnection && connectState !is ConnectCoupleState.Loading
                                    ) {
                                        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                                            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                                                if (error != null) {
                                                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                                                        return@loginWithKakaoTalk
                                                    }
                                                    UserApiClient.instance.loginWithKakaoAccount(
                                                        context, callback = kakaoCallback
                                                    )
                                                } else {
                                                    kakaoCallback(token, null)
                                                }
                                            }
                                        } else {
                                            UserApiClient.instance.loginWithKakaoAccount(
                                                context, callback = kakaoCallback
                                            )
                                        }
                                    })
                        }
                    }
                }

                // 👇 로딩 표시
                if (loginState is LoginState.Loading || connectState is ConnectCoupleState.Loading || loginState is LoginState.CheckingAutoLogin) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }

        // 커플 연결 모달
        if (showConnectModal) {
            ConnectBottomSheet(
                mySocialId = mySocialId,
                matchingViewModel = matchingViewModel,
                onDismiss = {
                    if (connectState !is ConnectCoupleState.Loading) {
                        showConnectModal = false
                        loginViewModel.resetState()
                        connectCoupleViewModel.resetState()
                    }
                },
                onConnect = { partnerCode ->
                    Log.d("LoginScreen", "커플 연결 콜백 → ViewModel 호출")
                    connectCoupleViewModel.connectCouple(partnerCode)
                },
                onMatchedNotification = {
                    Log.i("LoginScreen", "📩 매칭 알림 수신 → Home 이동")
                    showConnectModal = false
                    matchingViewModel.disconnectSocket()
                    navController.navigate(Route.Root.route) {
                        popUpTo(Route.Login.route) { inclusive = true }
                    }
                })
        }
    }
}