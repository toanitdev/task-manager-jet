package com.toanitdev.taskmanager.domain.interactors

import com.toanitdev.taskmanager.domain.entities.Project
import com.toanitdev.taskmanager.domain.repositories.ProjectRepository
import javax.inject.Inject

class GetAllProjects @Inject constructor(private val projectRepository: ProjectRepository) :
    UseCase<GetAllProjects.Param, List<Project>>() {
    override fun execute(param: Param): List<Project> {
        return projectRepository.getAllProject()
    }


    class Param()
}