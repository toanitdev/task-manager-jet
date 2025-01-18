package com.toanitdev.taskmanager.data.datasources.remote.retrofit

import android.content.Context
import com.toanitdev.taskmanager.BuildConfig.BASE_URL
import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.request.RefreshTokenRequest
import com.toanitdev.taskmanager.data.datasources.shared.SharedDataSource
import com.toanitdev.taskmanager.presentations.AuthStateController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RetrofitInstance @Inject constructor(
    private val applicationContext: Context
) {


    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val authOkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val authRetrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(authOkHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val authService: AuthService by lazy {
        authRetrofit.create(AuthService::class.java)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(AuthInterceptor(SharedDataSource(applicationContext), authService))
        .build()


    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}


class AuthInterceptor(
    val sharedDataSource: SharedDataSource,
    val authService: AuthService
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        return runBlocking {
            if (response.code == 401) {
                synchronized(this) {
                    return@runBlocking runBlocking {
                        val deferred = CoroutineScope(Dispatchers.IO).async() {
                            try {
                                val result =
                                    authService.refreshToken(RefreshTokenRequest(sharedDataSource.loadFreshToken()))
                                result.data?.accessToken?.let {
                                    sharedDataSource.saveAccessToken(it)
                                    return@async it
                                }
                            } catch (ex: Exception) {
                                sharedDataSource.removeAccessToken()
                                sharedDataSource.removeRefreshToken()
                                return@async ""
                            }
                            return@async ""
                        }
                        val result = deferred.await()
                        if(result.isNotEmpty()) {
                            response.close()
                            return@runBlocking chain.proceed(chain.request().newBuilder().header("Authorization", "Bearer $result").build())
                        }
                        AuthStateController.authStateListener?.onLogout()
                        return@runBlocking response
                    }
                }
            }
            return@runBlocking response
        }


    }

}