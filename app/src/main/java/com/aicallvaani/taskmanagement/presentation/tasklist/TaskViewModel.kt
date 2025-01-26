package com.aicallvaani.taskmanagement.presentation.tasklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val todoApiRepository: TodoApiRepository
): ViewModel() {
    val tasks: Flow<List<Task>> = repository.allTasks

    // remote api
    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos: StateFlow<List<Todo>> = _todos

    fun addTask(title: String, description: String) {
        viewModelScope.launch {
            repository.insert(Task(title = title, description = description, createdAt = System.currentTimeMillis()))
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

    fun toggleTaskCompletion(taskId: Int, isCompleted: Boolean) {
        viewModelScope.launch {
            repository.toggleTaskCompletion(taskId, isCompleted)
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
}