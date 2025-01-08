package com.toanitdev.taskmanager.data.datasources.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.toanitdev.taskmanager.data.datasources.local.room.dao.ProjectDao
import com.toanitdev.taskmanager.data.datasources.local.room.dao.TaskDao
import com.toanitdev.taskmanager.data.datasources.local.room.entities.ProjectEntity
import com.toanitdev.taskmanager.data.datasources.local.room.entities.TaskEntity


@Database(entities = [
    ProjectEntity::class,
    TaskEntity::class,
                     ], version = 2)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun projectDao() : ProjectDao
    abstract fun taskDao() : TaskDao

}
