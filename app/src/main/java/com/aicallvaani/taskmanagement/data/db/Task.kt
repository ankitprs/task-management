package com.aicallvaani.taskmanagement.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var title: String = "",
    var description: String = "",
    var isCompleted: Boolean = false,
    var createdAt: Long = System.currentTimeMillis(),
)
