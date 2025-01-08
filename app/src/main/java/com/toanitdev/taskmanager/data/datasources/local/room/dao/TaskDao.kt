package com.toanitdev.taskmanager.data.datasources.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.toanitdev.taskmanager.data.datasources.local.room.entities.ProjectWithTasks
import com.toanitdev.taskmanager.data.datasources.local.room.entities.TaskEntity


@Dao
interface TaskDao {


    @Query("SELECT * FROM TaskEntity  WHERE projectId=:projectId ")
    fun getAllTaskByProject(projectId : Int) : List<TaskEntity>

}