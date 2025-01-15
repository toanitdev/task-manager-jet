package com.toanitdev.taskmanager.domain.interactors

import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.request.LoginRequest
import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.response.BaseResponse
import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.response.LoginResponse
import com.toanitdev.taskmanager.domain.repositories.AuthRepository
import okhttp3.ResponseBody
import retrofit2.Call
import javax.inject.Inject

class SignInByAccount @Inject constructor(private val authRepository: AuthRepository): UseCase<SignInByAccount.Param, Call<BaseResponse<LoginResponse>>>() {




    override fun execute(param: Param): Call<BaseResponse<LoginResponse>> {
        return authRepository.login(LoginRequest(param.username, param.password))
    }

    data class Param(
        val username: String,
        val password: String
    )

}