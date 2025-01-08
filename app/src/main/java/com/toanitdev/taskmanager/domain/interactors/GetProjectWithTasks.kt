package com.toanitdev.taskmanager.domain.interactors

import com.toanitdev.taskmanager.domain.entities.Project
import com.toanitdev.taskmanager.domain.repositories.ProjectRepository
import javax.inject.Inject

class GetProjectWithTasks @Inject constructor(private val projectRepository: ProjectRepository) : UseCase<GetProjectWithTasks.Param, Project?>() {


    override fun execute(param: Param): Project? {
       return projectRepository.getProjectWithTask(param.projectId)
    }


    data class Param(

        val projectId: Int
    )

}