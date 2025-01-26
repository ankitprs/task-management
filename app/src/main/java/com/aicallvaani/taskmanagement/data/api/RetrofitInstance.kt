package com.aicallvaani.taskmanagement.data.api

import retrofit2.Retrofit


object RetrofitInstance {
    private const val BASE_URL = "https://dummyjson.com/"

    val api: TodoApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TodoApiService::class.java)
    }
}
