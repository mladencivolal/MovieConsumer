package com.example.movieconsumer.presentation.di.core

import android.content.Context
import com.example.movieconsumer.presentation.di.detail.DetailSubComponent
import com.example.movieconsumer.presentation.di.movies.MoviesSubComponent
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    subcomponents = [
        MoviesSubComponent::class,
        DetailSubComponent::class,
    ]
)
class AppModule(private val context: Context) {
    @Singleton
    @Provides
    fun provideApplicationContext(): Context = context.applicationContext
}