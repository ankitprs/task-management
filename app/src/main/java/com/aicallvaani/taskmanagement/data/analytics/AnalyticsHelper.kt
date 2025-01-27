package com.aicallvaani.taskmanagement.data.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class AnalyticsHelper(private val firebaseAnalytics: FirebaseAnalytics) {

    fun logTaskAdded(taskTitle: String) {
        val bundle = Bundle().apply {
            putString("task_title", taskTitle)
            putString("event_type", "Task Added")
        }
        firebaseAnalytics.logEvent("task_added", bundle)
    }

    fun logTaskEdited(taskTitle: String) {
        val bundle = Bundle().apply {
            putString("task_title", taskTitle)
            putString("event_type", "Task Edited")
        }
        firebaseAnalytics.logEvent("task_edited", bundle)
    }

    fun logTaskCompleted(taskTitle: String) {
        val bundle = Bundle().apply {
            putString("task_title", taskTitle)
            putString("event_type", "Task Completed")
        }
        firebaseAnalytics.logEvent("task_completed", bundle)
    }
}