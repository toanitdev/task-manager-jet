package com.toanitdev.taskmanager.data.datasources.remote

import com.toanitdev.taskmanager.data.datasources.remote.retrofit.ApiService
import com.toanitdev.taskmanager.data.datasources.remote.retrofit.RetrofitInstance
import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.request.LoginRequest
import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.response.BaseResponse
import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.response.LoginResponse
import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.response.UserProfileResponse
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.Call
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
   private val retrofitInstance: RetrofitInstance
) : RemoteDataSource {
    override suspend fun login(request: LoginRequest): BaseResponse<LoginResponse> {
        return retrofitInstance.apiService.login(request)
    }

    override suspend fun getUserProfile(accessToken: String) : BaseResponse<UserProfileResponse> {
        return retrofitInstance.apiService.getUserProfile(accessToken)
    }
}