package com.example.movieconsumer.presentation.di.movies

import com.example.movieconsumer.domain.usecase.GetMoviesUseCase
import com.example.movieconsumer.domain.usecase.LoadMoreMoviesUseCase
import com.example.movieconsumer.presentation.movies.MoviesViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class MoviesModule {
    @MoviesScope
    @Provides
    fun provideMoviesViewModelFactory(
        getMoviesUseCase: GetMoviesUseCase,
        loadMoreMoviesUseCase: LoadMoreMoviesUseCase
    ): MoviesViewModelFactory {
        return MoviesViewModelFactory(getMoviesUseCase, loadMoreMoviesUseCase)
    }
}