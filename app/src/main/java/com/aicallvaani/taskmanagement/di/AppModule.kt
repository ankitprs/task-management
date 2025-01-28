package com.aicallvaani.taskmanagement.di

import android.app.Application
import android.content.Context
import com.aicallvaani.taskmanagement.data.analytics.AnalyticsHelper
import com.aicallvaani.taskmanagement.data.api.RetrofitInstance
import com.aicallvaani.taskmanagement.data.repository.TodoApiRepository
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
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

    // google analytics
    @Provides
    @Singleton
    fun provideAnalyticsHelper(context: Context): AnalyticsHelper {
        val firebaseAnalytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(context)
        return AnalyticsHelper(firebaseAnalytics)
    }
}