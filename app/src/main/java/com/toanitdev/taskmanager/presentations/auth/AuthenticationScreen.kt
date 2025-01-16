package com.toanitdev.taskmanager.presentations.auth

import android.util.Log
import android.view.ViewTreeObserver
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.toanitdev.taskmanager.domain.entities.Project
import com.toanitdev.taskmanager.presentations.LocalNavigation
import com.toanitdev.taskmanager.presentations.ProjectPage
import com.toanitdev.taskmanager.ui.composable.InputText
import com.toanitdev.taskmanager.ui.composable.VSpacer
import com.toanitdev.taskmanager.ui.theme.TaskManagerTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun AuthenticationScreen(viewModel: AuthViewModel = hiltViewModel()) {
    var username by remember { mutableStateOf("toantran") }
    var password by remember { mutableStateOf("123456") }

    val view = LocalView.current
    var isKeyboardVisible by remember { mutableStateOf(false) }

    val loginState by viewModel.loginState.collectAsState()

    val navController = LocalNavigation.current
    DisposableEffect(view) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            isKeyboardVisible = ViewCompat.getRootWindowInsets(view)?.isVisible(WindowInsetsCompat.Type.ime()) ?: true
            Log.e("ToanLog", "$isKeyboardVisible")
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }


    Scaffold(
        contentWindowInsets = if (isKeyboardVisible) WindowInsets.statusBars else ScaffoldDefaults.contentWindowInsets
    ) { innerPadding ->
        Box(
            Modifier
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .imePadding()
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Column(
                Modifier.verticalScroll(rememberScrollState())
            ) {
                Text(
                    "Welcome to \nMineTasky",
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.headlineMedium
                )
                VSpacer(24.dp)
                Box(
                    Modifier.background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(12.dp)
                    )
                ) {
                    Column {
                        Column(
                            Modifier
                                .background(
                                    MaterialTheme.colorScheme.surface,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(16.dp)
                        ) {
                            Text(
                                "Sign In",
                                style = MaterialTheme.typography.headlineMedium
                            )
                            VSpacer(18.dp)
                            InputText(
                                label = "Username",
                                value = username,
                            ) { value ->
                                username = value
                            }
                            VSpacer(12.dp)
                            InputText(
                                label = "Password",
                                value = password,
                                visualTransformation = PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                            ) {value ->
                                password = value
                            }
                            VSpacer(12.dp)
                            TextButton(onClick = {

                            }) {
                                Text(
                                    "Forgot password?",
                                    color = MaterialTheme.colorScheme.secondary,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                            TextButton(onClick = {

                            }) {
                                Text(
                                    "Create new account",
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }

                        SignInButton {
                            viewModel.signIn(username,password)
                        }
                        when(loginState) {
                            is AuthViewModel.LoginState.Error -> {

                            }
                            AuthViewModel.LoginState.Idle -> {

                            }
                            AuthViewModel.LoginState.Loading -> {

                            }
                            AuthViewModel.LoginState.Success -> {
                                LaunchedEffect(Unit) {
                                    navController.navigate(ProjectPage) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            inclusive = true
                                        }
                                        launchSingleTop = true
                                    }

                                    viewModel.resetLoginState()
                                }
                            }
                        }
                    }
                }

                VSpacer(64.dp)
            }
        }
    }
}

@Composable
fun SignInButton(onClick:() -> Unit) {
    TextButton(onClick = {
        onClick()
    }) {
        Text(
            "Sign In".uppercase(),
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelMedium
        )
    }
}


@Composable
@Preview(showBackground = true)
fun AuthenticationScreenPreview() {

    TaskManagerTheme {
        AuthenticationScreen()
    }
}