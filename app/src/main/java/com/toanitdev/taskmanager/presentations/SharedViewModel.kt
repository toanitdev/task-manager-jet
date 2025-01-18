package com.toanitdev.taskmanager.presentations

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.toanitdev.taskmanager.data.datasources.shared.SharedDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SharedViewModel @Inject constructor(private val sharedDataSource: SharedDataSource) : ViewModel() {


    fun isLogin() : Boolean {
        return sharedDataSource.loadAccessToken().isNotEmpty()
    }


    fun logOut(onLogout: () -> Unit) {
        sharedDataSource.removeAccessToken()
        sharedDataSource.removeRefreshToken()
        onLogout()
    }


}

