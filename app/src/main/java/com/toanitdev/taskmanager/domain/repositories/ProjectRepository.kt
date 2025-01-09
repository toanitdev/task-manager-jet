package com.toanitdev.taskmanager.domain.repositories

import com.toanitdev.taskmanager.domain.entities.Project
import com.toanitdev.taskmanager.domain.entities.Task


interface ProjectRepository {

    fun getAllProject() : List<Project>
    fun getProjectWithTask(projectId: Int) : Project?
    fun addProject(project: Project)
    fun addTaskToProject(task: Task)


}