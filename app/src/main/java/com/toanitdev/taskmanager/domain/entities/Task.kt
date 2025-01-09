package com.toanitdev.taskmanager.domain.entities

import androidx.compose.ui.graphics.Color
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
) {

    enum class Priority(val label: String, val level: Int, val color: Color) {
        URGENT("Urgent", 1, color = Color(0xFFB71C1C)),
        HIGH("High", 2, color = Color(0xFFFF6F00)),
        NORMAL("Normal", 3, color = Color(0xFF01579B)),
        LOW("Low", 4, color = Color(0xFF689F38))
    }


    enum class Status(val label: String, val level: Int, val color: Color) {
        Todo("To do", 1, color = Color(0xFF838383)),
        OnProgress("On Progress", 2, color = Color(0xFF01579B)),
        Done("Done", 3, color = Color(0xFF689F38)),
    }

}
