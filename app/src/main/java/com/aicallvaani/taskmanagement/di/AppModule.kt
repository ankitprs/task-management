package com.aicallvaani.taskmanagement.di

import android.app.Application
import android.content.Context
import com.aicallvaani.taskmanagement.data.api.RetrofitInstance
import com.aicallvaani.taskmanagement.data.repository.TodoApiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    // retrofit
    @Provides
    @Singleton
    fun provideTodoApiRepository(): TodoApiRepository {
        return TodoApiRepository(RetrofitInstance.api)
    }
}