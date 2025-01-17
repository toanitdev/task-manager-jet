package com.toanitdev.taskmanager.presentations.splashScreen

import android.graphics.drawable.PaintDrawable
import android.window.SplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.toanitdev.taskmanager.R
import com.toanitdev.taskmanager.presentations.AuthenticationPage
import com.toanitdev.taskmanager.presentations.LocalNavigation
import com.toanitdev.taskmanager.presentations.ProjectPage
import com.toanitdev.taskmanager.presentations.SharedViewModel
import com.toanitdev.taskmanager.ui.composable.VSpacer
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(sharedViewModel: SharedViewModel = hiltViewModel()) {
    val navController = LocalNavigation.current
    LaunchedEffect(Unit) {
        delay(3000)
        if (sharedViewModel.isLogin()) {
            navController.navigate(ProjectPage) {
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
                launchSingleTop = true

            }
        } else {
            navController.navigate(AuthenticationPage) {
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
                launchSingleTop = true

            }
        }
    }

    Box(
        Modifier
            .background(Color(0xffe20c16))
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.mipmap.ic_launcher_foreground),
                "", Modifier.scale(3f)
            )
            VSpacer(36.dp)
            CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}


@Composable
@Preview
fun SplashScreenPreview() {
    SplashScreen()
}