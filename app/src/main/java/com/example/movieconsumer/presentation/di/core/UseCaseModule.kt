package com.example.movieconsumer.presentation.di.core

import com.example.movieconsumer.domain.repository.MoviesRepository
import com.example.movieconsumer.domain.usecase.GetMoviesUseCase
import com.example.movieconsumer.domain.usecase.LoadMoreMoviesUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {
    @Singleton
    @Provides
    fun provideGetMoviesUseCase(moviesRepository: MoviesRepository): GetMoviesUseCase {
        return GetMoviesUseCase(moviesRepository)
    }

    @Singleton
    @Provides
    fun provideLoadMoreMoviesUseCase(moviesRepository: MoviesRepository): LoadMoreMoviesUseCase {
        return LoadMoreMoviesUseCase(moviesRepository)
    }
}