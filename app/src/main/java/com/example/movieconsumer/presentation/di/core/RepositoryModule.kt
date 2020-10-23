package com.example.movieconsumer.presentation.di.core

import com.example.movieconsumer.data.repository.detail.dataSource.DetailRemoteDataSource
import com.example.movieconsumer.data.repository.detail.dataSourceImpl.DetailRepositoryImpl
import com.example.movieconsumer.data.repository.movie.dataSource.MoviesRemoteDataSource
import com.example.movieconsumer.data.repository.movie.dataSourceImpl.MoviesRepositoryImpl
import com.example.movieconsumer.domain.repository.DetailRepository
import com.example.movieconsumer.domain.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideMoviesRepository(
        repoRemoteDataSource: MoviesRemoteDataSource
    ): MoviesRepository {
        return MoviesRepositoryImpl(
            repoRemoteDataSource
        )
    }

    @Singleton
    @Provides
    fun provideDetailRepository(
        detailRemoteDataSource: DetailRemoteDataSource
    ): DetailRepository {
        return DetailRepositoryImpl(
            detailRemoteDataSource
        )
    }
}