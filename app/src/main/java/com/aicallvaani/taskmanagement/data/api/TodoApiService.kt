package com.aicallvaani.taskmanagement.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface TodoApiService {
    @GET("todos")
    suspend fun getTodos(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): TodoResponse
}