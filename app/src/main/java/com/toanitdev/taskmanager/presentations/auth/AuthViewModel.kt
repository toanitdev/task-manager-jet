package com.toanitdev.taskmanager.presentations.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.response.BaseResponse
import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.response.LoginResponse
import com.toanitdev.taskmanager.domain.interactors.GetAllProjects
import com.toanitdev.taskmanager.domain.interactors.SignInByAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInByAccount: SignInByAccount
) : ViewModel() {


    fun signIn(username: String, password: String) {
        signInByAccount.execute(SignInByAccount.Param(username, password))
            .enqueue(object : Callback<BaseResponse<LoginResponse>> {
                override fun onResponse(p0: Call<BaseResponse<LoginResponse>>, p1: Response<BaseResponse<LoginResponse>>) {
                    Log.d("ToanDev", "SS::1:httpCode: ${p1.code()} token: ${p1.body()?.data?.access_token}")
                }

                override fun onFailure(p0: Call<BaseResponse<LoginResponse>>, p1: Throwable) {
                    Log.d("ToanDev", "F::2:${p1.message}")
                }


            })
    }
}