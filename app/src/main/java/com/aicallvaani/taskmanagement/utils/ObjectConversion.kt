package com.aicallvaani.taskmanagement.utils

import com.aicallvaani.taskmanagement.data.api.Todo
import com.aicallvaani.taskmanagement.data.db.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ObjectConversion {
}

fun convertTodosToTasks(todos: List<Todo>): Flow<List<Task>> {
    return flow {
        emit(
            todos.map { todo ->
                Task(
                    id = todo.id, // Preserve the same ID if needed
                    title = todo.todo, // Use `todo` as the title
                    description = "Task from user ${todo.userId}", // Add user-specific info in the description
                    isCompleted = todo.completed,
                    createdAt = System.currentTimeMillis() // Use the current time as the creation time
                )
            }
        )
    }
}