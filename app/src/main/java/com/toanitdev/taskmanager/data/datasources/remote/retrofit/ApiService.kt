package com.toanitdev.taskmanager.data.datasources.remote.retrofit

import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.request.LoginRequest
import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.request.RefreshTokenRequest
import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.response.BaseResponse
import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.response.LoginResponse
import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.response.RefreshTokenResponse
import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.response.UserProfileResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST



interface ApiService {

    @POST("/api/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest) : BaseResponse<LoginResponse>


    @GET("/api/user/my_profile")
    suspend fun getUserProfile(@Header("Authorization") accessToken: String) : BaseResponse<UserProfileResponse>

}

interface AuthService {
    @POST("/api/auth/refresh_token")
    suspend fun refreshToken(@Body refreshTokenRequest: RefreshTokenRequest) : BaseResponse<RefreshTokenResponse>

}