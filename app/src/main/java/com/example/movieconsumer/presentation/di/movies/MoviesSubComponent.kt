package com.example.movieconsumer.presentation.di.movies

import com.example.movieconsumer.presentation.movies.MoviesActivity
import dagger.Subcomponent

@MoviesScope
@Subcomponent(modules = [MoviesModule::class])
interface MoviesSubComponent {
    fun inject(moviesActivity: MoviesActivity)

    @Subcomponent.Factory
    interface Factory {
        fun create():MoviesSubComponent
    }
}