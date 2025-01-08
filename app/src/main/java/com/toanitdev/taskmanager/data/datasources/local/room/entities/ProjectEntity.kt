package com.toanitdev.taskmanager.data.datasources.local.room.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


@Entity
data class ProjectEntity(
    @PrimaryKey(autoGenerate = true) val projectId: Int = 0,
    @ColumnInfo val name: String,
    @ColumnInfo val description: String,
    @ColumnInfo val createdTime: String, // yyyy-MM-dd HH:mm:ss
    @ColumnInfo val priority: Int, // 1 UGRENT, 2 High, 3 Normal, 4 Low
    @ColumnInfo val type: Int //1 private, 2 public
)


data class ProjectWithTasks (
    @Embedded
    val projectEntity: ProjectEntity,
    @Relation (
        parentColumn = "projectId",
        entityColumn = "projectId",
    )
    val taskList: List<TaskEntity>
)



