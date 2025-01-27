package com.aicallvaani.taskmanagement.data.repository

import com.aicallvaani.taskmanagement.data.db.Task
import com.aicallvaani.taskmanagement.data.db.TaskDao
import kotlinx.coroutines.flow.Flow

class TaskRepository (private val taskDao: TaskDao) {
    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()

    suspend fun insert(task: Task) = taskDao.insertTask(task)

    suspend fun deleteAll() = taskDao.deleteAllTasks()

    suspend fun toggleTaskCompletion(taskId: Int, isCompleted: Boolean) {
        taskDao.toggleIsCompleted(taskId, isCompleted)
    }

    suspend fun generateError() {
        taskDao.generateError(Task())
    }
}
