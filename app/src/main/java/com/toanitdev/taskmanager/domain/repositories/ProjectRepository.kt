package com.toanitdev.taskmanager.domain.repositories

import com.toanitdev.taskmanager.domain.entities.Project


interface ProjectRepository {

    fun getAllProject() : List<Project>
    fun getProjectWithTask(projectId: Int) : Project?
    fun addProject(project: Project)


}