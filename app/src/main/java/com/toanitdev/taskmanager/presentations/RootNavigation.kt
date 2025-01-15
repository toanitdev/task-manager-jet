package com.toanitdev.taskmanager.presentations

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.toanitdev.taskmanager.core.serializableType
import com.toanitdev.taskmanager.domain.entities.Project
import com.toanitdev.taskmanager.presentations.addTasks.AddTaskScreen
import com.toanitdev.taskmanager.presentations.auth.AuthenticationScreen
import com.toanitdev.taskmanager.presentations.project.ProjectScreen
import com.toanitdev.taskmanager.presentations.projectDetails.ProjectDetailScreen
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf


val LocalNavigation = staticCompositionLocalOf<NavHostController> { error("Not provided") }

@Composable
fun RootNavigation() {

    val navController = rememberNavController()
    CompositionLocalProvider(
        LocalNavigation provides navController
    ) {



        NavHost(navController = navController,
            startDestination = AuthenticationPage) {
            composable<AuthenticationPage> {
                AuthenticationScreen()
            }
            composable<ProjectPage> {
                ProjectScreen() { page ->
                    navController.navigate(page)
                }
            }
            composable<ProjectDetailsPage>(
                typeMap = mapOf(typeOf<Project>() to serializableType<Project>())
            ) {  backStackEntry ->

                val project = backStackEntry.toRoute<ProjectDetailsPage>().project
                project.projectId?.let { ProjectDetailScreen(it) }
            }
            composable<ProjectDetailsPage>(
                typeMap = mapOf(typeOf<Project>() to serializableType<Project>())
            ) {  backStackEntry ->

                val project = backStackEntry.toRoute<ProjectDetailsPage>().project
                project.projectId?.let { ProjectDetailScreen(it) }
            }

            composable<AddTaskPage>{  backStackEntry ->
                val projectId = backStackEntry.toRoute<AddTaskPage>().projectId
                AddTaskScreen(projectId)
            }

        }
    }

}

@Serializable
object ProjectPage

@Serializable
object AuthenticationPage

@Serializable
data class ProjectDetailsPage(val project: Project)

@Serializable
data class AddTaskPage(val projectId: Int)

