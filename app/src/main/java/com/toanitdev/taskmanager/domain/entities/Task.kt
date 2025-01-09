package com.toanitdev.taskmanager.domain.entities

import kotlinx.serialization.Serializable


@Serializable
data class Task(
    val name: String,
    val description: String,
    val priority: String,
    val status: String, // "to_do" -> "on_progress" -> "done"
    val startDate: String,
    val endDate: String,
    val createDate: String,
    val estimateTime: Int,
    val storyPoint: Int,
    var projectId: Int
)
