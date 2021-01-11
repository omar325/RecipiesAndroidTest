package com.example.recipiestest

import android.app.Application
import com.example.recipiestest.infrastructure.RecipesApi
import com.example.recipiestest.ui.RecipesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class RecipeTest : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@RecipeTest)
            modules(
                module {
                    viewModel { RecipesViewModel(get()) }
                    single<RecipesApi> { RecipesApi.Default.create() }
                }
            )
        }
    }
}