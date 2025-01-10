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

    enum class Priority(
        val label: String,
        val level: Int,
        val contentColor: Color,
        val containerColor: Color
    ) {
        URGENT("Urgent", 1,  Color(0xFFB71C1C),  Color(0xFFEF9A9A)),
        HIGH("High", 2,  Color(0xFFFF6F00),  Color(0xFFFFAB91)),
        NORMAL("Normal", 3,  Color(0xFF01579B),  Color(0xFF90CAF9)),
        LOW("Low", 4,  Color(0xFF689F38), Color(0xFFA5D6A7))
    }


    enum class Status(val label: String, val level: Int, val color: Color) {
        Todo("To do", 1, color = Color(0xFF838383)),
        OnProgress("On Progress", 2, color = Color(0xFF01579B)),
        Done("Done", 3, color = Color(0xFF689F38)),
    }

}
