package com.aicallvaani.taskmanagement.data.api

import com.google.firebase.perf.network.FirebasePerfOkHttpClient
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    private const val BASE_URL = "https://dummyjson.com"

    private val client = OkHttpClient.Builder().addInterceptor { chain ->
        FirebasePerfOkHttpClient.execute(chain.call())
    }.build()


    val api: TodoApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TodoApiService::class.java)
    }
}
