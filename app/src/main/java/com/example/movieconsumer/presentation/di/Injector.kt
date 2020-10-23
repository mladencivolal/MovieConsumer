package com.example.movieconsumer.presentation.di

import com.example.movieconsumer.presentation.di.detail.DetailSubComponent
import com.example.movieconsumer.presentation.di.movies.MoviesSubComponent

interface Injector {
    fun createMovieSubComponent(): MoviesSubComponent
    fun createDetailSubComponent(): DetailSubComponent
}