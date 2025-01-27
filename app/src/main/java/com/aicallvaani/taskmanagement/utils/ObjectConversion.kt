package com.aicallvaani.taskmanagement.utils

import com.aicallvaani.taskmanagement.data.api.Todo
import com.aicallvaani.taskmanagement.data.db.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


fun convertTodosToTasks(todos: List<Todo>): Flow<List<Task>> {
    return flow {
        emit(
            todos.map { todo ->
                Task(
                    id = todo.id,
                    title = todo.todo,
                    description = "Task from user ${todo.userId}",
                    isCompleted = todo.completed,
                    createdAt = System.currentTimeMillis()
                )
            }
        )
    }
}