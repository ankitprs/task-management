package com.aicallvaani.taskmanagement.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.aicallvaani.taskmanagement.presentation.tasklist.TaskScreen
import com.aicallvaani.taskmanagement.presentation.ui.theme.TaskManagementTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskManagementTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                }
                TaskScreen()
            }
        }
    }
}