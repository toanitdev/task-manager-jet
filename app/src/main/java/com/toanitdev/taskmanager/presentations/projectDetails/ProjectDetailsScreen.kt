package com.toanitdev.taskmanager.presentations.projectDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.twotone.List
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.toanitdev.taskmanager.domain.entities.Project
import com.toanitdev.taskmanager.domain.entities.Task
import com.toanitdev.taskmanager.presentations.AddTaskPage
import com.toanitdev.taskmanager.presentations.LocalNavigation
import com.toanitdev.taskmanager.presentations.project.ProjectViewmodel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ProjectDetailScreen(projectId: Int, viewmodel: ProjectViewmodel = hiltViewModel()) {

    val projectState = remember {
        mutableStateOf<Project?>(null)
    }
    val navController = LocalNavigation.current
    LaunchedEffect("") {
        CoroutineScope(Dispatchers.IO).launch {
            projectState.value = viewmodel.getProjectWithTask(projectId)
        }
    }

    Scaffold { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text("${projectState.value?.name}", fontSize = 21.sp)
            Text(
                "${projectState.value?.description}",
                fontSize = 13.sp,
                color = LocalContentColor.current.copy(alpha = 0.6f)
            )

            projectState.value?.let { project ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Tasks", fontSize = 18.sp)
                    Spacer(Modifier.weight(1f))
                    IconButton(onClick = {
                        navController.navigate(AddTaskPage(projectId = projectId))
                    }) {
                        Icon(imageVector = Icons.Filled.Add, "")
                    }
                }
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(project.tasks) { item ->
                        TaskItem(item)
                    }
                }
            }
        }


    }
}


@Composable
fun TaskItem(task: Task) {
    val taskPriority = Task.Priority.entries.first { it.level == task.priority.toInt() }
    val taskStatus = Task.Status.entries.first { it.level == task.status.toInt() }

    Card {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier
                    .background(color = taskStatus.color, shape = CircleShape)
                    .height(24.dp)
                    .width(24.dp)
            ) {}

            Text(task.name, Modifier.weight(1f))
            VerticalDivider()
            Box(
                Modifier
                    .width(70.dp)
                    .background(
                        taskPriority.color.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(percent = 50)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(taskPriority.label, color = taskPriority.color, fontSize = 13.sp)
            }
            VerticalDivider()
            IconButton({

            }, Modifier.size(24.dp)) {
                Icon(imageVector = Icons.Filled.ArrowDropDown, "")
            }
        }
    }
}


@Preview
@Composable
fun ProjectDetailScreenPreview() {
//    ProjectDetailScreen()
}