package com.example.dulit.feature.user.presentaion

import android.util.Log
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
import androidx.navigation.NavHostController
import com.example.dulit.R
import com.example.dulit.core.network.RetrofitClient
import com.example.dulit.core.ui.theme.DulitNavy
import com.example.dulit.core.ui.theme.DulitNavy50
import com.example.dulit.feature.user.data.repository.UserRepositoryImpl
import com.example.dulit.feature.user.domain.usecase.KakaoLoginUseCase
import com.example.dulit.navigation.Route
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dulit.feature.user.data.api.AuthApi

@Composable
fun LoginScreen(navController: NavHostController) {
    val context = LocalContext.current

    // 의존성 주입 (나중에 Hilt로 대체 가능)
    val authApi = RetrofitClient.create(AuthApi::class.java)
    val userRepository = UserRepositoryImpl(authApi)
    val kakaoLoginUseCase = KakaoLoginUseCase(userRepository)
    val viewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(kakaoLoginUseCase)
    )

    val loginState by viewModel.loginState.collectAsState()

    // 로그인 상태 관찰
    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginState.Success -> {
                val user = (loginState as LoginState.Success).user
                Log.i("LoginScreen", "로그인 성공: ${user.name}")
                navController.navigate(Route.Root.route) {
                    popUpTo(Route.Login.route) { inclusive = true }
                }
            }
            is LoginState.Error -> {
                Log.e("LoginScreen", (loginState as LoginState.Error).message)
                // TODO: 에러 메시지를 UI에 표시
            }
            else -> {}
        }
    }

    // 카카오 로그인 콜백
    val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e("KakaoLogin", "카카오 로그인 실패", error)
        } else if (token != null) {
            Log.i("KakaoLogin", "카카오 토큰: ${token.accessToken}")
            // ViewModel을 통해 백엔드 로그인
            viewModel.kakaoLogin(token.accessToken)
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

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 32.dp)
                    ) {
                        Divider(modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.outlineVariant)
                        Text(
                            text = "또는",
                            modifier = Modifier.padding(horizontal = 8.dp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Divider(modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.outlineVariant)
                    }

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.naver_login_round),
                            contentDescription = "네이버 로그인",
                            modifier = Modifier.size(50.dp)
                        )
                        Spacer(modifier = Modifier.width(32.dp))
                        Image(
                            painter = painterResource(id = R.drawable.apple_login_round),
                            contentDescription = "애플 로그인",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
            }

            // 로딩 인디케이터
            if (loginState is LoginState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}