package com.toanitdev.taskmanager.data.datasources.local.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(foreignKeys = [
    ForeignKey(
        entity = ProjectEntity::class,
        parentColumns = ["projectId"],
        childColumns = ["projectId"],
        onDelete = ForeignKey.CASCADE
    )
])
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val taskId: Int = 0,
    @ColumnInfo val name: String,
    @ColumnInfo val description: String,
    @ColumnInfo val priority: String,
    @ColumnInfo val status: String, // "to_do" -> "on_progress" -> "done"
    @ColumnInfo val startDate: String,
    @ColumnInfo val endDate: String,
    @ColumnInfo val createDate: String,
    @ColumnInfo val estimateTime: Int,
    @ColumnInfo val storyPoint: Int,
    @ColumnInfo var projectId: Int
)


