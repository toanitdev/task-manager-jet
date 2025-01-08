package com.toanitdev.taskmanager.data.datasources.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.toanitdev.taskmanager.data.datasources.local.room.entities.ProjectEntity
import com.toanitdev.taskmanager.data.datasources.local.room.entities.ProjectWithTasks


@Dao
interface ProjectDao {

    @Query("SELECT * FROM ProjectEntity")
    fun getAllProjects() : List<ProjectEntity>

    @Insert
    fun insertNewProject(project: ProjectEntity)


    @Query("SELECT * FROM ProjectEntity  WHERE projectId=:projectId ")
    fun getProjectWithTasks(projectId : Int) : List<ProjectWithTasks>
}