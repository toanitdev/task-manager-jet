package com.toanitdev.taskmanager.data.datasources.remote.retrofit

import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.request.LoginRequest
import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.response.BaseResponse
import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.response.LoginResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST



interface ApiService {

    @POST("/api/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest) : BaseResponse<LoginResponse>

}