package com.toanitdev.taskmanager.presentations.auth

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toanitdev.taskmanager.data.datasources.remote.middleware.ApiResult
import com.toanitdev.taskmanager.data.datasources.remote.middleware.CallFailure
import com.toanitdev.taskmanager.domain.interactors.SignInByAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInByAccount: SignInByAccount
) : ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState = _loginState.asStateFlow()

    sealed class LoginState {
        object Idle: LoginState()
        object Loading: LoginState()
        object Success: LoginState()
        data class Error(val message: String): LoginState()
    }

    fun resetLoginState() {
        _loginState.value = LoginState.Idle
    }

    fun signIn(username: String, password: String) {

        viewModelScope.launch {

            _loginState.value = LoginState.Loading
            signInByAccount.execute(SignInByAccount.Param(username, password)).collect { result ->
                when(result) {
                    is ApiResult.Success -> {
                        _loginState.value = LoginState.Success
                        Log.d("ToanDev", "SS::1:username: ${result.data.username} token: ${result.data.accessToken}")

                    }

                    is ApiResult.Failure -> {
                        when(val failure = result.failure) {
                            is CallFailure.HttpFailure -> {
                                _loginState.value = LoginState.Error(message = failure.message)
                                Log.d("ToanDev", "FF::1:message: ${failure.message} code: ${failure.code}")
                            }
                            is CallFailure.IOFailure -> {

                                _loginState.value = LoginState.Error(message = failure.message)
                                Log.d("ToanDev", "FF::2:message:  ${failure.message}")
                            }
                            is CallFailure.UnknownFailure -> {

                                _loginState.value = LoginState.Error(message = failure.message)
                                Log.d("ToanDev", "FF::3:message:  ${failure.message}")
                            }
                        }
                    }
                }
            }
        }

    }
}