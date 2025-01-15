package com.toanitdev.taskmanager.domain.repositories

import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.request.LoginRequest
import com.toanitdev.taskmanager.domain.entities.Project
import com.toanitdev.taskmanager.domain.entities.Task
import okhttp3.ResponseBody
import retrofit2.Call


interface ProjectRepository {

    fun getAllProject() : List<Project>
    fun getProjectWithTask(projectId: Int) : Project?
    fun addProject(project: Project)
    fun addTaskToProject(task: Task)


}