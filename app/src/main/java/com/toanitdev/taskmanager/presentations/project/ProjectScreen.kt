package com.toanitdev.taskmanager.presentations.project

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.toanitdev.taskmanager.domain.entities.Project
import com.toanitdev.taskmanager.presentations.LocalNavigation
import com.toanitdev.taskmanager.presentations.ProjectDetailsPage
import com.toanitdev.taskmanager.ui.composable.InputText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectScreen(viewmodel: ProjectViewmodel = hiltViewModel(), onNavigate: (Any) -> Unit) {

    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    var skipPartiallyExpanded by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = skipPartiallyExpanded)
    val scope = rememberCoroutineScope()


    var list = remember { mutableStateListOf<Project>() }

    LaunchedEffect("") {

        CoroutineScope(Dispatchers.IO).launch {
            list.clear()
            list.addAll(viewmodel.getAllProject().toMutableStateList())
        }
    }


    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {

        TopAppBar(title = { Text("Projects", fontSize = 28.sp, color = Color(0xFF3e25ab)) },
            actions = {

                IconButton(onClick = {
                    openBottomSheet = !openBottomSheet
                }) {
                    Icon(imageVector = Icons.Filled.Add, "")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFFd4ceff)
            )
        )

    }, containerColor = Color(0xFFd4ceff)) { innerPadding ->

        Column(
            modifier = Modifier.padding(innerPadding)
        ) {


            Spacer(modifier = Modifier.height(16.dp))
            Box {
                LazyColumn() {
                    itemsIndexed(list) { index, item ->
                        ProjectItemView(
                            Color.White,
                            item.name,
                            item.description,
                            item.createdTime
                        ) {
                            onNavigate(ProjectDetailsPage(item))
                        }
                    }
                }
            }
        }
        if (openBottomSheet) {
            AddProjectBottomSheet(
                onDismissRequest = { openBottomSheet = false },
                onAdd = { project ->
                    CoroutineScope(Dispatchers.IO).launch {
                        viewmodel.addNewProject(project)
                        list = viewmodel.getAllProject().toMutableStateList()
                    }
                },
                sheetState = bottomSheetState
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProjectBottomSheet(
    onDismissRequest: () -> Unit,
    onAdd: (Project) -> Unit,
    sheetState: SheetState
) {
    ModalBottomSheet(onDismissRequest = onDismissRequest, sheetState = sheetState) {
        var name by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row {
                InputText(
                    Modifier
                        .padding(16.dp)
                        .weight(1f), "Project name",
                    value = name,
                    onValueChange = {
                        name = it
                    }
                )
            }
            Row {
                InputText(
                    Modifier.padding(16.dp), "Project description",
                    value = description,
                    onValueChange = {
                        description = it
                    }
                )
            }
            Button(onClick = {
                onAdd(
                    Project(
                        name = name, description = description,
                        createdTime = LocalDateTime.now()
                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        priority = 0,
                        type = 0,
                        projectId = null,
                        tasks = emptyList()
                    )
                )
            }) {
                Text("Add")
            }
        }

    }
}


enum class CardColor(
    val color: Color
) {
    Red(Color(0xFFFFCDD2)),
    Blue(Color(0xFF4FC3F7)),
    Green(Color(0xFFAED581)),
    Yellow(Color(0xFFFFD54F));

    companion object {
        fun getRandom(): CardColor {
            val colors = entries.toTypedArray()
            return colors[Random.nextInt(colors.size)]

        }
    }
}

@Composable
fun ProjectItemView(
    color: Color,
    name: String,
    description: String,
    dateTime: String,
    onClicked: () -> Unit
) {
    Box(Modifier.padding(16.dp)) {
        Card(colors = CardColors(
            color,
            contentColor = Color.Black,
            disabledContainerColor = color,
            disabledContentColor = Color.Black
        ), modifier = Modifier.clickable {
            onClicked()
        }) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .height(IntrinsicSize.Min)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        name,
                        fontSize = 21.sp,
                        color = Color(0xFF1d64a9),
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        description,
                        color = Color(0xFF64B5F6),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis, fontSize = 13.sp,
                        lineHeight = 13.sp
                    )
                }

                VerticalDivider(thickness = 2.dp, color = Color(0xFF64B5F6))

                getDate(dateTime)?.let {
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(
                        modifier = Modifier
                            .width(75.dp)
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("${it.year}", color = Color(0xFF64B5F6), fontSize = 24.sp)

                        HorizontalDivider(thickness = 2.dp, color = Color(0xFF64B5F6))
                        Row {
                            Text(
                                toDateInMonth(it),
                                color = Color(0xFF64B5F6),
                                fontSize = 24.sp
                            )
                        }
                        HorizontalDivider(thickness = 2.dp, color = Color(0xFF64B5F6))

                        Text(
                            toStringTime(it),
                            color = Color(0xFF64B5F6),
                            fontSize = 24.sp
                        )
                    }
                }

            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ProjectScreenPreview() {
//    ProjectScreen() {}
//}
//


@Preview(showBackground = true, backgroundColor = 0xFFd4ceff)
@Composable
fun InputTextPreview() {
    val text = remember { mutableStateOf<String>("ádasdasd") }
    InputText(
        Modifier.padding(16.dp), "Label",
        value = text.value,
        onValueChange = {
            text.value = it
        }
    )
}



fun getDate(dateString: String): LocalDateTime? {

    val format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val localDateTime: LocalDateTime? = LocalDateTime.parse(dateString, format)
    return localDateTime
}

fun toStringTime(localDateTime: LocalDateTime): String {
    val format = DateTimeFormatter.ofPattern("HH:mm")
    return localDateTime.format(format)
}

fun toDateInMonth(localDateTime: LocalDateTime): String {
    val format = DateTimeFormatter.ofPattern("MM/dd")
    return localDateTime.format(format)
}