package com.toanitdev.taskmanager.presentations.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.toanitdev.taskmanager.presentations.AuthStateController
import com.toanitdev.taskmanager.presentations.AuthenticationPage
import com.toanitdev.taskmanager.presentations.LocalNavigation
import com.toanitdev.taskmanager.presentations.SharedViewModel

@Composable
fun ProfileScreen(sharedViewModel: SharedViewModel = hiltViewModel()) {

    val navController = LocalNavigation.current
    Scaffold { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            TextButton({
                sharedViewModel.logOut {
                    AuthStateController.authStateListener?.onLogout()
                }
            }) {
                Text("Log out")
            }
        }
    }
}


@Preview
@Composable
fun ProfileScreenPreview() {

}