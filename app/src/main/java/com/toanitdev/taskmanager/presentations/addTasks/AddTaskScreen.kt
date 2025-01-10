package com.toanitdev.taskmanager.presentations.addTasks

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sd.lib.compose.wheel_picker.FVerticalWheelPicker
import com.sd.lib.compose.wheel_picker.rememberFWheelPickerState
import com.toanitdev.taskmanager.core.helper.getCurrentDateTimeString
import com.toanitdev.taskmanager.domain.entities.Task
import com.toanitdev.taskmanager.presentations.project.ProjectViewmodel
import com.toanitdev.taskmanager.ui.composable.InputText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter


val storyPoint = arrayOf(1, 2, 3, 5, 8, 13, 21, 34)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(projectId: Int, viewmodel: ProjectViewmodel = hiltViewModel()) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var estimateTime by remember { mutableStateOf("") }
    var openPrioritySheet by remember { mutableStateOf(false) }
    var prioritySheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    var priorityWheelState = rememberFWheelPickerState(initialIndex = 0)

    var openStatusSheet by remember { mutableStateOf(false) }
    var statusSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    var statusWheelState = rememberFWheelPickerState(initialIndex = 0)

    var openESTSheet by remember { mutableStateOf(false) }
    var estSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    var estWheelState = rememberFWheelPickerState(initialIndex = 0)

    var openStoryPointSheet by remember { mutableStateOf(false) }
    var storyPointSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    var storyPointWheelState = rememberFWheelPickerState(initialIndex = 0)


    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var showDatePicker by remember { mutableStateOf(false) }
    var selectFor by remember { mutableIntStateOf(1) }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }

    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    Scaffold() { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {


            Text("Project: $projectId ")
            InputText(
                label = "Title", value = name,
                modifier = Modifier.height(48.dp)
            ) { value ->
                name = value
            }
            InputText(
                label = "Description", value = description,
                modifier = Modifier.height(48.dp)
            ) { value ->
                description = value
            }

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Column(
                    Modifier
                        .weight(1f)
                        .clickable {
                            openPrioritySheet = !openPrioritySheet
                        }
                ) {
                    Text("Priority", fontSize = 12.sp)
                    Spacer(Modifier.height(6.dp))
                    Box(
                        Modifier
                            .height(48.dp)
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceContainerHigh, shape = RoundedCornerShape(12.dp))
                    ) {
                        if (priorityWheelState.currentIndex >= 0) {
                            Text(Task.Priority.entries[priorityWheelState.currentIndex].label, modifier = Modifier.align(Alignment.Center))
                        }
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            "",
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(8.dp)
                        )
                    }
                }
                Column(
                    Modifier
                        .weight(1f)
                        .clickable {
                            openStatusSheet = !openStatusSheet
                        }
                ) {
                    Text("Status", fontSize = 12.sp)
                    Spacer(Modifier.height(6.dp))
                    Box(
                        Modifier
                            .height(48.dp)
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceContainerHigh, shape = RoundedCornerShape(12.dp))
                    ) {
                        if (statusWheelState.currentIndex >= 0) {
                            Text(Task.Status.entries[statusWheelState.currentIndex].label, modifier = Modifier.align(Alignment.Center))
                        }
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown, "", modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(8.dp)
                        )
                    }
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Column(
                    Modifier
                        .weight(1f)
                        .clickable {
                            showDatePicker = !showDatePicker
                            selectFor = 1
                        }
                ) {
                    Text("Start date", fontSize = 12.sp)
                    Spacer(Modifier.height(6.dp))
                    Box(
                        Modifier
                            .height(48.dp)
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceContainerHigh, shape = RoundedCornerShape(12.dp))
                    ) {
                        Text(startDate, modifier = Modifier.align(Alignment.Center))

                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            "",
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(8.dp)
                        )
                    }
                }
                Column(
                    Modifier
                        .weight(1f)
                        .clickable {
                            showDatePicker = !showDatePicker
                            selectFor = 2
                        }
                ) {
                    Text("End date", fontSize = 12.sp)
                    Spacer(Modifier.height(6.dp))
                    Box(
                        Modifier
                            .height(48.dp)
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceContainerHigh, shape = RoundedCornerShape(12.dp))
                    ) {
                        Text(endDate, modifier = Modifier.align(Alignment.Center))

                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown, "", modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(8.dp)
                        )
                    }
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Column(
                    Modifier
                        .weight(1f)
                ) {
                    InputText(
                        label = "Estimate time", value = estimateTime,
                        modifier = Modifier.height(48.dp).fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    ) { value ->

                        if (value.all { it.isDigit() }) { // Only allow digits
                            estimateTime = value
                        }
                    }
                }
                Column(
                    Modifier
                        .weight(1f)
                        .clickable {
                            openStoryPointSheet = !openStoryPointSheet
                        }
                ) {
                    Text("Story points", fontSize = 12.sp)
                    Spacer(Modifier.height(6.dp))
                    Box(
                        Modifier
                            .height(48.dp)
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceContainerHigh, shape = RoundedCornerShape(12.dp))
                    ) {
                        if (storyPointWheelState.currentIndex >= 0) {
                            Text("${storyPoint[storyPointWheelState.currentIndex]}", modifier = Modifier.align(Alignment.Center))
                        }
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown, "", modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(8.dp)
                        )
                    }
                }
            }

            Button(onClick = {

                CoroutineScope(Dispatchers.IO).launch {
                    viewmodel.addTasksToProject(projectId, Task(
                        name = name,
                        description = description,
                        priority = Task.Priority.entries[priorityWheelState.currentIndex].level.toString(),
                        status = Task.Status.entries[statusWheelState.currentIndex].level.toString(),
                        startDate = startDate,
                        endDate = endDate,
                        createDate = getCurrentDateTimeString(),
                        estimateTime = estimateTime.toInt(),
                        storyPoint = storyPoint[storyPointWheelState.currentIndex],
                        projectId = projectId
                    ))
                }
            }) {
                Text("Add Task")
            }
        }

        if (openStatusSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    openStatusSheet = false
                },
                sheetState = statusSheetState,
            ) {
                FVerticalWheelPicker(
                    modifier = Modifier.fillMaxWidth(),
                    count = Task.Status.entries.size,
                    state = statusWheelState
                ) { index ->
                    Text(Task.Status.entries[index].label)
                }
            }
        }

        if (openPrioritySheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    openPrioritySheet = false
                },
                sheetState = prioritySheetState,
            ) {
                FVerticalWheelPicker(
                    modifier = Modifier.fillMaxWidth(),
                    count = Task.Priority.entries.size,
                    state = priorityWheelState
                ) { index ->
                    Text(Task.Priority.entries[index].label)
                }
            }
        }

        if (openStoryPointSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    openStoryPointSheet = false
                },
                sheetState = storyPointSheetState,
            ) {
                FVerticalWheelPicker(
                    modifier = Modifier.fillMaxWidth(),
                    count = storyPoint.size,
                    state = storyPointWheelState
                ) { index ->
                    Text("${storyPoint[index]}")
                }
            }
        }

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Confirm")
                    }
                }
            ) {
                val datePickerState = rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())
                DatePicker(state = datePickerState)

                LaunchedEffect(datePickerState.selectedDateMillis) {
                    datePickerState.selectedDateMillis?.let {
                        selectedDate = LocalDate.ofEpochDay(it / (1000 * 60 * 60 * 24))
                        if (selectFor ==  1) {
                            startDate = selectedDate.format(dateFormatter)
                        } else {
                            endDate = selectedDate.format(dateFormatter)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun PrioritySheet() {
    FVerticalWheelPicker(
        modifier = Modifier.width(60.dp),
        // Specified item count.
        count = Task.Priority.entries.size,
    ) { index ->
        Text(Task.Priority.entries[index].label)
    }
}
