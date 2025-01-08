package com.toanitdev.taskmanager.domain.entities

import kotlinx.serialization.Serializable


@Serializable
data class Project(
    val projectId: Int? = null,
    val name: String,
    val description: String,
    val createdTime: String, // yyyy-MM-dd HH:mm:ss
    val priority: Int, // 1 UGRENT, 2 High, 3 Normal, 4 Low
    val type: Int, //1 private, 2 public
    val tasks: List<Task>
)