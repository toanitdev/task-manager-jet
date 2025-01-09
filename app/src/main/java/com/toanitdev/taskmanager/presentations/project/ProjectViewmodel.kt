package com.toanitdev.taskmanager.presentations.project

import androidx.lifecycle.ViewModel
import com.toanitdev.taskmanager.domain.entities.Project
import com.toanitdev.taskmanager.domain.entities.Task
import com.toanitdev.taskmanager.domain.interactors.AddProject
import com.toanitdev.taskmanager.domain.interactors.AddTaskToProject
import com.toanitdev.taskmanager.domain.interactors.GetAllProjects
import com.toanitdev.taskmanager.domain.interactors.GetProjectWithTasks
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ProjectViewmodel @Inject constructor(
    private val getAllProjects: GetAllProjects,
    private val addProject: AddProject,
    private val getProjectWithTasks: GetProjectWithTasks,
    private val addTaskToProject: AddTaskToProject
) : ViewModel() {

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
}