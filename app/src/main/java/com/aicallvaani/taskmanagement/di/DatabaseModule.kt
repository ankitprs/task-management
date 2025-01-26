package com.aicallvaani.taskmanagement.di

import android.content.Context
import com.aicallvaani.taskmanagement.data.repository.TaskRepository
import com.aicallvaani.taskmanagement.data.db.TaskDao
import com.aicallvaani.taskmanagement.data.db.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Context): TaskDatabase {
        return TaskDatabase.getDatabase(app)
    }

    @Provides
    @Singleton
    fun provideTaskDao(database: TaskDatabase): TaskDao {
        return database.taskDao()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository {
        return TaskRepository(taskDao)
    }
}