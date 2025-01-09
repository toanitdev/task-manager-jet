package com.toanitdev.taskmanager.data.repositories

import com.toanitdev.taskmanager.data.datasources.local.room.RoomDataSource
import com.toanitdev.taskmanager.data.datasources.local.room.entities.ProjectEntity
import com.toanitdev.taskmanager.data.datasources.local.room.entities.TaskEntity
import com.toanitdev.taskmanager.data.datasources.remote.RemoteDataSource
import com.toanitdev.taskmanager.domain.entities.Project
import com.toanitdev.taskmanager.domain.entities.Task
import com.toanitdev.taskmanager.domain.repositories.ProjectRepository
import javax.inject.Inject

class ProjectRepositoryImpl @Inject constructor(
    private val roomDataSource: RoomDataSource,
    private val remoteDatasource: RemoteDataSource,
) : ProjectRepository {


    override fun getAllProject(): List<Project> {
        return roomDataSource.getAllProject().map {
            Project(
                it.projectId,
                it.name,
                it.description,
                it.createdTime,
                it.priority,
                it.type,
                tasks = emptyList(),
            )
        }
    }

    override fun getProjectWithTask(projectId: Int): Project? {


        if (roomDataSource.getProjectWithTask(projectId).isEmpty()) {
            return null

        }
        val result =  roomDataSource.getProjectWithTask(projectId).first()
        val project = result.projectEntity
        val task = result.taskList.map { Task(
            name = it.name,
            description = it.name,
            priority = it.priority,
            status = it.status,
            startDate = it.startDate,
            endDate = it.endDate,
            createDate = it.createDate,
            estimateTime = it.estimateTime,
            storyPoint = it.storyPoint,
            projectId = it.projectId
        ) }

        return  Project(
            project.projectId,
            project.name,
            project.description,
            project.createdTime,
            project.priority,
            project.type,
            tasks = task,
        )
    }

    override fun addProject(project: Project) {
        return roomDataSource.insertProject(
            ProjectEntity(
                name = project.name,
                description = project.description,
                createdTime = project.createdTime,
                priority = project.priority,
                type = project.type
            )
        )
    }

    override fun addTaskToProject(task: Task) {
        roomDataSource.addTaskToProject(
            TaskEntity(
                name = task.name,
                description = task.description,
                priority = task.priority,
                status = task.status,
                startDate = task.startDate,
                endDate = task.endDate,
                createDate = task.createDate,
                estimateTime = task.estimateTime,
                storyPoint = task.storyPoint,
                projectId = task.projectId
            )
        )
    }
}