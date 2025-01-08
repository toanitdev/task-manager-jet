package com.toanitdev.taskmanager.presentations.projectDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
                color = Color.Black.copy(alpha = 0.6f)
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
                LazyColumn {
                    items(project.tasks) { item ->
                        Text(item.name)
                    }
                }
            }
        }


    }
}


@Preview
@Composable
fun ProjectDetailScreenPreview() {
//    ProjectDetailScreen()
}