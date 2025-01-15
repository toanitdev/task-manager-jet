package com.toanitdev.taskmanager.data.datasources.remote

import com.toanitdev.taskmanager.data.datasources.remote.retrofit.ApiService
import com.toanitdev.taskmanager.data.datasources.remote.retrofit.RetrofitInstance
import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.request.LoginRequest
import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.response.BaseResponse
import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.response.LoginResponse
import okhttp3.ResponseBody
import retrofit2.Call
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor() : RemoteDataSource {
    override fun login(request: LoginRequest): Call<BaseResponse<LoginResponse>> {
        return RetrofitInstance.apiService.login(request)
    }
}