package com.example.dulit.feature.user.presentaion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dulit.feature.user.domain.usecase.KakaoLoginUseCase

class LoginViewModelFactory(
    private val kakaoLoginUseCase: KakaoLoginUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(kakaoLoginUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}