package com.example.movieconsumer.presentation.di.core

import com.example.movieconsumer.domain.repository.DetailRepository
import com.example.movieconsumer.domain.repository.MoviesRepository
import com.example.movieconsumer.domain.usecase.*
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

    @Singleton
    @Provides
    fun provideGetMovieDetailsUseCase(detailRepository: DetailRepository):GetMovieDetailsUseCase {
        return GetMovieDetailsUseCase(detailRepository)
    }

    @Singleton
    @Provides
    fun provideGetActorsFromMovieUseCase(detailRepository: DetailRepository):GetActorsFromMovieUseCase {
        return GetActorsFromMovieUseCase(detailRepository)
    }

    @Singleton
    @Provides
    fun provideGetTrailersForMovieUseCase(detailRepository: DetailRepository):GetTrailersForMovieUseCase {
        return GetTrailersForMovieUseCase(detailRepository)
    }
}