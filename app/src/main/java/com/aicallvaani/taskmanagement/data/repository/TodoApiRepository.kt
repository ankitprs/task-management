package com.aicallvaani.taskmanagement.data.repository

import com.aicallvaani.taskmanagement.data.api.TodoApiService
import com.aicallvaani.taskmanagement.data.api.TodoResponse

class TodoApiRepository(private val api: TodoApiService)  {

    suspend fun fetchTodos(limit: Int, skip: Int): TodoResponse {
        return api.getTodos(limit, skip)
    }
}