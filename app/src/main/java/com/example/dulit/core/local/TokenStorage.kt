package com.example.dulit.core.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class TokenStorage(private val context: Context) {

    private val masterKey by lazy {
        MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }

    private val sharedPreferences: SharedPreferences by lazy {
        EncryptedSharedPreferences.create(
            context,
            "token_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_SOCIAL_ID = "social_id"  // 👈 추가
    }

    fun saveAccessToken(token: String) {
        sharedPreferences.edit()
            .putString(KEY_ACCESS_TOKEN, token)
            .apply()
    }

    fun getAccessToken(): String? {
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, null)
    }

    fun saveRefreshToken(token: String) {
        sharedPreferences.edit()
            .putString(KEY_REFRESH_TOKEN, token)
            .apply()
    }

    fun getRefreshToken(): String? {
        return sharedPreferences.getString(KEY_REFRESH_TOKEN, null)
    }

    // 👇 socialId 관련 메서드 추가
    fun saveSocialId(socialId: String) {
        sharedPreferences.edit()
            .putString(KEY_SOCIAL_ID, socialId)
            .apply()
    }

    fun getSocialId(): String? {
        return sharedPreferences.getString(KEY_SOCIAL_ID, null)
    }

    fun clearTokens() {
        sharedPreferences.edit().clear().apply()
    }

    fun hasAccessToken(): Boolean {
        return getAccessToken() != null
    }

    fun hasSocialId(): Boolean {
        return getSocialId() != null
    }
}