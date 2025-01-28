package com.aicallvaani.taskmanagement.presentation.tasklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aicallvaani.taskmanagement.data.analytics.AnalyticsHelper
import com.aicallvaani.taskmanagement.data.api.Todo
import com.aicallvaani.taskmanagement.data.repository.TaskRepository
import com.aicallvaani.taskmanagement.data.db.Task
import com.aicallvaani.taskmanagement.data.repository.TodoApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository,
    private val todoApiRepository: TodoApiRepository,
    private val analyticsHelper: AnalyticsHelper
): ViewModel() {
    var tasks: Flow<List<Task>> = repository.allTasks

    // remote api
    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos: StateFlow<List<Todo>> = _todos

    fun addTask(title: String, description: String, editableTask: Task?) {
        viewModelScope.launch {
            val task = if (editableTask != null) {
                // log event
                analyticsHelper.logTaskEdited(title)
                Task(id = editableTask.id, title = title, description = description, createdAt = System.currentTimeMillis())
            } else {
                // log event
                analyticsHelper.logTaskAdded(title)
                Task(title = title, description = description, createdAt = System.currentTimeMillis())
            }

            repository.insert(task)
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
                _todos.value = response.todos
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun generateRoomDatabaseError() {
        viewModelScope.launch {
            repository.generatingError()
        }
    }
}