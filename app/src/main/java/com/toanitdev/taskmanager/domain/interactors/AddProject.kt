package com.toanitdev.taskmanager.domain.interactors

import com.toanitdev.taskmanager.domain.entities.Project
import com.toanitdev.taskmanager.domain.repositories.ProjectRepository
import javax.inject.Inject

class AddProject @Inject constructor(private val projectRepository: ProjectRepository) :
    UseCase<AddProject.Param, Any>() {
    override fun execute(param: AddProject.Param) {
        projectRepository.addProject(param.project)
    }

    data class Param(val project: Project)
}