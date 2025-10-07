package com.example.dulit

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DulitApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 앱이 시작될 때 딱 한 번만 실행됨
        KakaoSdk.init(this, getString(R.string.kakao_native_app_key))
    }
}