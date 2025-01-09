package com.toanitdev.taskmanager.data.datasources.local.room

import com.toanitdev.taskmanager.data.datasources.local.room.entities.ProjectEntity
import com.toanitdev.taskmanager.data.datasources.local.room.entities.ProjectWithTasks
import com.toanitdev.taskmanager.data.datasources.local.room.entities.TaskEntity
import javax.inject.Inject

class RoomDataSource @Inject constructor(private val toDoDB: TodoDatabase) {

    fun getAllProject(): List<ProjectEntity> {
        return toDoDB.projectDao().getAllProjects()
    }

    fun insertProject(project: ProjectEntity) {
        toDoDB.projectDao().insertNewProject(project)
    }



    fun getProjectWithTask(projectId: Int): List<ProjectWithTasks> {
        return toDoDB.projectDao().getProjectWithTasks(projectId)
    }

    fun addTaskToProject(taskEntity: TaskEntity) {
        toDoDB.projectDao().insertTask(taskEntity)
    }

}