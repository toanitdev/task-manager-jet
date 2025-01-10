package com.toanitdev.taskmanager

import android.R.id.content
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.toanitdev.taskmanager.presentations.RootNavigation
import com.toanitdev.taskmanager.presentations.project.ProjectScreen
import com.toanitdev.taskmanager.presentations.project.ProjectViewmodel
import com.toanitdev.taskmanager.ui.theme.TaskManagerTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        setContent {

            TaskManagerTheme {
                RootNavigation()
            }
        }
    }
}

