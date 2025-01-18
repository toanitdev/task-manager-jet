package com.toanitdev.taskmanager.data.datasources.remote.retrofit

import android.content.Context
import com.toanitdev.taskmanager.BuildConfig.BASE_URL
import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.request.RefreshTokenRequest
import com.toanitdev.taskmanager.data.datasources.shared.SharedDataSource
import com.toanitdev.taskmanager.presentations.AuthStateController
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
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
                        println("ToanAuth:: FF Token InValid")
                        val deferred = CoroutineScope(Dispatchers.IO).async() {
                            try {
                                val result =
                                    authService.refreshToken(RefreshTokenRequest(sharedDataSource.loadFreshToken()))
                                result.data?.accessToken?.let {
                                    sharedDataSource.saveAccessToken(it)

                                    println("ToanAuth:: REFRESH Success")
                                    return@async it
                                }

                            } catch (ex: Exception) {

                                println("ToanAuth:: REFRESH ERROR")
                                sharedDataSource.removeAccessToken()
                                sharedDataSource.removeRefreshToken()
                                return@async ""
                            }

                            println("ToanAuth:: REFRESH ERROR")
                            return@async ""
                        }

                        val result = deferred.await()
                        if(result.isNotEmpty()) {

                            println("ToanAuth:: Try request again")
                            response.close()
                            return@runBlocking chain.proceed(chain.request().newBuilder().header("Authorization", "Bearer $result").build())
                        }

                        println("ToanAuth:: REFRESH ERROR :: Return Error 1st request")
                        AuthStateController.authStateListener?.onLogout()
                        return@runBlocking response
                    }
                }

            }
            println("ToanAuth:: SS Token Valid")
            return@runBlocking response
        }


    }

}