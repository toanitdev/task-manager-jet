package com.toanitdev.taskmanager.presentations.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toanitdev.taskmanager.data.datasources.remote.middleware.ApiResult
import com.toanitdev.taskmanager.data.datasources.remote.middleware.CallFailure
import com.toanitdev.taskmanager.domain.interactors.SignInByAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInByAccount: SignInByAccount
) : ViewModel() {


    fun signIn(username: String, password: String) {

        viewModelScope.launch {

            signInByAccount.execute(SignInByAccount.Param(username, password)).collect { result ->
                when(result) {
                    is ApiResult.Success -> {

                        Log.d("ToanDev", "SS::1:username: ${result.data.username} token: ${result.data.accessToken}")
                    }

                    is ApiResult.Failure -> {
                        when(val failure = result.failure) {
                            is CallFailure.HttpFailure -> {

                                Log.d("ToanDev", "FF::1:message: ${failure.message} code: ${failure.code}")
                            }
                            is CallFailure.IOFailure -> {

                                Log.d("ToanDev", "FF::2:message:  ${failure.message}")
                            }
                            is CallFailure.UnknownFailure -> {

                                Log.d("ToanDev", "FF::3:message:  ${failure.message}")
                            }
                        }
                    }
                }
            }
        }

    }
}