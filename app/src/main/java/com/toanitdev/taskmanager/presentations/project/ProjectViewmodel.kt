package com.toanitdev.taskmanager.presentations.project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toanitdev.taskmanager.data.datasources.remote.middleware.ApiResult
import com.toanitdev.taskmanager.domain.entities.Project
import com.toanitdev.taskmanager.domain.entities.Task
import com.toanitdev.taskmanager.domain.entities.User
import com.toanitdev.taskmanager.domain.interactors.AddProject
import com.toanitdev.taskmanager.domain.interactors.AddTaskToProject
import com.toanitdev.taskmanager.domain.interactors.GetAllProjects
import com.toanitdev.taskmanager.domain.interactors.GetProjectWithTasks
import com.toanitdev.taskmanager.domain.interactors.GetUserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProjectViewmodel @Inject constructor(
    private val getAllProjects: GetAllProjects,
    private val addProject: AddProject,
    private val getProjectWithTasks: GetProjectWithTasks,
    private val addTaskToProject: AddTaskToProject,
    private val getUserProfile: GetUserProfile
) : ViewModel() {

    private val _userState = MutableStateFlow<UserState>(UserState.Idle)
    val userState = _userState.asStateFlow()


    fun getAllProject() : List<Project> {
        return getAllProjects.execute(GetAllProjects.Param())
    }

    fun addNewProject(project: Project) {
        val param = AddProject.Param(project)
        addProject.execute(param)
    }

    fun getProjectWithTask(projectId: Int) : Project? {
        val param = GetProjectWithTasks.Param(projectId)
        return getProjectWithTasks.execute(param)
    }

    fun addTasksToProject(projectId: Int, task: Task) {
        task.projectId = projectId
        addTaskToProject.execute(AddTaskToProject.Param(task))
    }

    fun getProfile() {
        viewModelScope.launch {
            _userState.value = UserState.Loading
            getUserProfile.execute(GetUserProfile.Param()).collect{ result ->
                when(result) {
                    is ApiResult.Failure -> {
                        _userState.value = UserState.Error
                    }
                    is ApiResult.Success -> {

                        _userState.value = UserState.Success(result.data)
                    }
                }

            }
        }
    }

    sealed class UserState {
        object Idle : UserState()
        object Loading : UserState()
        data class Success(val data: User) : UserState()
        object Error : UserState()
    }
}