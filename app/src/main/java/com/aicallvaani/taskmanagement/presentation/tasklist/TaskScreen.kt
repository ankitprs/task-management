package com.aicallvaani.taskmanagement.presentation.tasklist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aicallvaani.taskmanagement.data.db.Task
import com.aicallvaani.taskmanagement.presentation.components.SettingSheet
import com.aicallvaani.taskmanagement.presentation.components.TaskDialog
import com.aicallvaani.taskmanagement.presentation.components.TaskItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(viewModel: TaskViewModel = hiltViewModel()) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    val tasks by viewModel.tasks.collectAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    var editableTask by remember { mutableStateOf<Task?>(null) }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showDialog = true },
                icon = { Icon(Icons.Filled.Add, "create task") },
                text = { Text(text = "New Task") }
            )
        },
        topBar = {
            LargeTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "My Tasks",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    IconButton(onClick = { showBottomSheet = true }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (showDialog) {
                TaskDialog(
                    editableTask,
                    onAddTask = {title, description ->
                        viewModel.addTask(title, description, editableTask)
                        showDialog = false
                    },
                    onDismiss = { showDialog = false; editableTask = null }
                )
            }

            LazyColumn(contentPadding = PaddingValues(0.dp)) {
                items(tasks.size) { index ->
                    val task = tasks[index]
                    TaskItem(
                        task = task,
                        onEditTask = { showDialog = true; editableTask = task },
                        onToggleComplete = {
                            viewModel.toggleTaskCompletion(task.id, task.title, !task.isCompleted)
                        }
                    )
                }
            }
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                // Sheet content
                SettingSheet(
                    onCrashDatabase = {
                        viewModel.generateRoomDatabaseError()
                    },
                    onSwitchApiList = { viewModel.fetchTodos(20, 0) },
                    onDeleteAllTasks = { viewModel.deleteAllTasks() }
                )
            }
        }
    }
}
