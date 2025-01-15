package com.toanitdev.taskmanager.domain.repositories

import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.request.LoginRequest
import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.response.BaseResponse
import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.response.LoginResponse
import okhttp3.ResponseBody
import retrofit2.Call

interface AuthRepository {


    fun login(request: LoginRequest) : Call<BaseResponse<LoginResponse>>
}