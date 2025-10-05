package com.example.dulit.feature.user.presentaion

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.dulit.R
import com.example.dulit.core.ui.theme.DulitTheme
import com.example.dulit.core.ui.theme.DulitNavy // 레거시 색상 직접 import
import com.example.dulit.core.ui.theme.DulitNavy50 // 레거시 색상 직접 import
import com.example.dulit.navigation.Route

@Composable
fun LoginScreen(navController: NavHostController) {
    // Surface를 사용하여 앱의 기본 배경색을 적용합니다.
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // 1. 타이틀 부분
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "둘잇",
                    // [수정] Typography와 ColorScheme에서 스타일을 가져옵니다.
                    style = MaterialTheme.typography.displayLarge,
                    color = DulitNavy // 로그인 화면 전용 레거시 색상
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "두 사람을 이어주는 특별한 공간",
                    // [수정] Typography와 ColorScheme에서 스타일을 가져옵니다.
                    style = MaterialTheme.typography.bodyLarge,
                    color = DulitNavy50 // 로그인 화면 전용 레거시 색상
                )
            }

            // 2. 로그인 버튼 그룹
            Column(
                modifier = Modifier.padding(all = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 카카오 로그인 버튼
                Image(
                    painter = painterResource(id = R.drawable.kakao_login),
                    contentDescription = "카카오 로그인",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Route.Root.route) {
                                popUpTo(Route.Login.route) {
                                    inclusive = true
                                }
                            }
                        }
                )

                // "또는" 구분선
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 32.dp)
                ) {
                    // [수정] 구분선 색상을 테마에서 가져옵니다.
                    Divider(modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.outlineVariant)
                    Text(
                        text = "또는",
                        modifier = Modifier.padding(horizontal = 8.dp),
                        // [수정] 텍스트 색상을 테마에서 가져옵니다.
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Divider(modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.outlineVariant)
                }

                // 하단 소셜 로그인 버튼 (네이버, 애플)
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
    }
}
