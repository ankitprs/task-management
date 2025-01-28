package com.aicallvaani.taskmanagement.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM task_table ORDER BY id DESC")
    fun getAllTasks(): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Query("UPDATE task_table SET isCompleted = :isCompleted WHERE id = :taskId")
    suspend fun toggleIsCompleted(taskId: Int, isCompleted: Boolean)

    @Query("DELETE FROM task_table")
    suspend fun deleteAllTasks()

    // error
    @Query("SELECT *, (1/0) AS 'error' FROM task_table")
    suspend fun generateError(): List<Task>
}