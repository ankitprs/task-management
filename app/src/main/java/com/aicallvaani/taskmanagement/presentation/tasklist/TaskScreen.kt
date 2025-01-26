package com.aicallvaani.taskmanagement.presentation.tasklist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aicallvaani.taskmanagement.R
import com.aicallvaani.taskmanagement.presentation.components.FullScreenTaskDialog
import com.aicallvaani.taskmanagement.presentation.components.TaskDialog
import com.aicallvaani.taskmanagement.presentation.components.TaskItem


@Composable
fun TaskScreen(viewModel: TaskViewModel = hiltViewModel()) {
    val tasks by viewModel.tasks.collectAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Image(painter = painterResource(R.drawable.round_add_24), contentDescription = "add icon")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (showDialog) {
                FullScreenTaskDialog(
                    onDismiss = { showDialog = false },
                    onTaskCreated = { title, description ->
                        // Handle task creation (e.g., save to database)
                        viewModel.addTask(title, description)
                        showDialog = false
                    }
                )
//                TaskDialog(
//                    onAddTask = { title, description ->
//                        viewModel.addTask(title, description)
//                        showDialog = false
//                    },
//                    onDismiss = { showDialog = false }
//                )
            }
            Text(
                modifier = Modifier.padding(vertical = 32.dp),
                text = "My Tasks",
                fontSize = 28.sp
            )
            LazyColumn {
                items(tasks.size) { index ->
                    val task = tasks[index]
                    TaskItem(
                        task = task,
                        onDelete = { viewModel.deleteTask(it) },
                        onToggleComplete = {
                            viewModel.toggleTaskCompletion(task.id, !task.isCompleted)
                        }
                    )
                }
            }
        }
    }
}
