package com.toanitdev.taskmanager.data.datasources.shared

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import javax.inject.Inject

class SharedDataSource @Inject constructor(private val context: Context) {


    private val ACCESS_TOKEN_KEY = "access_token"
    private val REFRESH_TOKEN_KEY = "refresh_token"

    fun saveAccessToken(token: String) {
        val encryptedSharedPreferences = getEncryptedSharedPreferences(context)
        val editor = encryptedSharedPreferences.edit()

        editor.putString(ACCESS_TOKEN_KEY, token)
        editor.apply()
    }

    fun saveFreshToken(token: String) {
        val encryptedSharedPreferences = getEncryptedSharedPreferences(context)
        val editor = encryptedSharedPreferences.edit()

        editor.putString(REFRESH_TOKEN_KEY, token)
        editor.apply()
    }

    fun loadAccessToken() : String {
        val encryptedSharedPreferences = getEncryptedSharedPreferences(context)
        return encryptedSharedPreferences.getString(ACCESS_TOKEN_KEY, "") ?: ""
    }

    fun loadFreshToken() : String {
        val encryptedSharedPreferences = getEncryptedSharedPreferences(context)
        return encryptedSharedPreferences.getString(REFRESH_TOKEN_KEY, "") ?: ""
    }


    fun removeAccessToken() {
        val encryptedSharedPreferences = getEncryptedSharedPreferences(context)
        encryptedSharedPreferences.edit().remove(ACCESS_TOKEN_KEY).apply()
    }

    fun removeRefreshToken() {
        val encryptedSharedPreferences = getEncryptedSharedPreferences(context)
        encryptedSharedPreferences.edit().remove(REFRESH_TOKEN_KEY).apply()
    }

    fun getEncryptedSharedPreferences(context: Context): EncryptedSharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            "SecurePrefs", // Tên file SharedPreferences
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        ) as EncryptedSharedPreferences
    }
}