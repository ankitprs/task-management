package com.aicallvaani.taskmanagement.presentation.tasklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aicallvaani.taskmanagement.data.analytics.AnalyticsHelper
import com.aicallvaani.taskmanagement.data.api.Todo
import com.aicallvaani.taskmanagement.data.repository.TaskRepository
import com.aicallvaani.taskmanagement.data.db.Task
import com.aicallvaani.taskmanagement.data.repository.TodoApiRepository
import com.aicallvaani.taskmanagement.utils.convertTodosToTasks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository,
    private val todoApiRepository: TodoApiRepository,
    private val analyticsHelper: AnalyticsHelper
): ViewModel() {
    var tasks: Flow<List<Task>> = repository.allTasks
//        flowOf(emptyList())
//    =

    // remote api
    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos: StateFlow<List<Todo>> = _todos

    init {
        fetchTodos(30, 0)
    }

    fun addTask(title: String, description: String) {
        viewModelScope.launch {
            repository.insert(Task(title = title, description = description, createdAt = System.currentTimeMillis()))

            // log event
            analyticsHelper.logTaskAdded(title)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.delete(task)
        }
    }

    fun deleteAllTasks() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }

    fun toggleTaskCompletion(taskId: Int, title: String, isCompleted: Boolean) {
        viewModelScope.launch {
            repository.toggleTaskCompletion(taskId, isCompleted)

            // log event
            if (isCompleted)
                analyticsHelper.logTaskCompleted(title)
        }
    }

    fun fetchTodos(limit: Int, skip: Int) {
        viewModelScope.launch {
            try {
                val response = todoApiRepository.fetchTodos(limit, skip)
                tasks = convertTodosToTasks(response.todos)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun generateRoomDatabaseError() {
        viewModelScope.launch {
            repository.generateError()
        }
    }
}