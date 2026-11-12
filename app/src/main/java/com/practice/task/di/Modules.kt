package com.practice.task.di

import com.practice.task.network.API
import com.practice.task.repositories.HomeRepository
import com.practice.task.repositories.RegistrationRepository
import com.practice.task.viewmodels.HomeViewModel
import com.practice.task.viewmodels.RegisterationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    fun provideNetwork(): API {
        return Retrofit.Builder().baseUrl("https://codexia.xyz/food/public/api/app/")
            .addConverterFactory(GsonConverterFactory.create()).build().create(API::class.java)
    }

    single { provideNetwork() }
}

val repositoryModules = module {
    single { HomeRepository(get()) }
    single { RegistrationRepository(get()) }
}

val viewModelModules = module {
    viewModel { HomeViewModel(get()) }
    viewModel { RegisterationViewModel(get()) }
}