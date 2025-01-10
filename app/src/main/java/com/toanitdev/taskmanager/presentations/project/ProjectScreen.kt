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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.twotone.DateRange
import androidx.compose.material.icons.twotone.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.toanitdev.taskmanager.domain.entities.Project
import com.toanitdev.taskmanager.presentations.ProjectDetailsPage
import com.toanitdev.taskmanager.ui.composable.InputText
import com.toanitdev.taskmanager.ui.composable.VSpacer
import com.toanitdev.taskmanager.ui.theme.Green
import com.toanitdev.taskmanager.ui.theme.White
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectScreen(viewmodel: ProjectViewmodel = hiltViewModel(), onNavigate: (Any) -> Unit) {

    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val skipPartiallyExpanded by rememberSaveable { mutableStateOf(true) }
    val bottomSheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = skipPartiallyExpanded)
    var list = remember { mutableStateListOf<Project>() }

    LaunchedEffect("") {

        CoroutineScope(Dispatchers.IO).launch {
            list.clear()
            list.addAll(viewmodel.getAllProject().toMutableStateList())
        }
    }


    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {

        TopAppBar(
            title = {
                Text(
                    "Your projects",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.headlineMedium
                )
            },
            actions = {

                IconButton(onClick = {

                }) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        "",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        )

    }, containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = { openBottomSheet = !openBottomSheet }) {
                Icon(imageVector = Icons.Rounded.Add, "",
                    modifier = Modifier.size(40.dp),
                    tint = MaterialTheme.colorScheme.onPrimary)
            }
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
            )
            Column {


                Spacer(modifier = Modifier.height(16.dp))
                Box {
                    LazyColumn {
                        itemsIndexed(list) { _, item ->
                            ProjectItemView(
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
    ModalBottomSheet(
        modifier = Modifier
            .imePadding(),
        onDismissRequest = onDismissRequest, sheetState = sheetState) {
        var name by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
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
                    Modifier
                        .padding(16.dp), "Project description",
                    value = description,
                    onValueChange = {
                        description = it
                    }
                )
            }
            VSpacer()
            Button(modifier = Modifier.fillMaxWidth(), onClick = {
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
                Text(
                    "Add",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}


@Composable
fun ProjectItemView(
    name: String,
    description: String,
    dateTime: String,
    onClicked: () -> Unit
) {

    val bgColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9f)
    Box(Modifier.padding(16.dp)) {
        Card(
            colors = CardColors(
                containerColor = bgColor,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledContainerColor = bgColor,
                disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            ),
            modifier = Modifier.clickable {
                onClicked()
            }) {

            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .height(IntrinsicSize.Min),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        Modifier
                            .background(Green, shape = CircleShape)
                            .size(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            name.first().uppercase(),
                            color = White,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Column(
                        Modifier.weight(1f)
                    ) {
                        Text(
                            name,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.titleLarge
                        )

                        getDate(dateTime)?.let {

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.TwoTone.DateRange,
                                    "",
                                    tint = LocalContentColor.current.copy(alpha = 0.7f),
                                    modifier = Modifier.size(15.dp)
                                )
                                Text(
                                    it.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = LocalContentColor.current.copy(alpha = 0.7f),
                                )
                            }
                        }
                    }
                    IconButton({}) {

                        Icon(
                            imageVector = Icons.TwoTone.MoreVert,
                            "",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                    }
                }

                HorizontalDivider()
                Text(
                    description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = LocalContentColor.current.copy(alpha = 0.7f),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
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


@Preview(showBackground = true, backgroundColor = 0xFFf4e1dd)
@Composable
fun InputTextPreview() {
    val text = remember { mutableStateOf("ádasdasd") }
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

