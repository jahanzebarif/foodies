package com.practice.task.app

import android.app.Application
import com.practice.task.di.networkModule
import com.practice.task.di.repositoryModules
import com.practice.task.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin { androidContext(this@Application)
        modules(listOf(networkModule, repositoryModules, viewModelModules))}
    }
}