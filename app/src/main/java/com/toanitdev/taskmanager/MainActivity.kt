package com.toanitdev.taskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.toanitdev.taskmanager.presentations.RootNavigation
import com.toanitdev.taskmanager.presentations.project.ProjectScreen
import com.toanitdev.taskmanager.presentations.project.ProjectViewmodel
import com.toanitdev.taskmanager.ui.theme.TaskManagerTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            TaskManagerTheme {
                RootNavigation()
            }
        }
    }
}

