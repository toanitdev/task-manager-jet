package com.toanitdev.taskmanager.domain.interactors

import com.toanitdev.taskmanager.domain.entities.Task
import com.toanitdev.taskmanager.domain.repositories.ProjectRepository
import javax.inject.Inject

class AddTaskToProject @Inject constructor(private val projectRepository: ProjectRepository): UseCase<AddTaskToProject.Param, Any>() {



    data class Param(
        val task: Task
    )

    override fun execute(param: Param) {
        projectRepository.addTaskToProject(param.task)
    }
}