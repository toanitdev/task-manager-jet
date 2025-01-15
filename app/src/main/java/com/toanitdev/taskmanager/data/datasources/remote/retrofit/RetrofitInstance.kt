package com.toanitdev.taskmanager.data.datasources.remote.retrofit

import com.toanitdev.taskmanager.BuildConfig.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}