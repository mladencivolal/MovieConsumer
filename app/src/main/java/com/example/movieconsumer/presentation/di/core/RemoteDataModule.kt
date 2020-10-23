package com.example.movieconsumer.presentation.di.core

import com.example.movieconsumer.data.api.TMDBService
import com.example.movieconsumer.data.repository.detail.dataSource.DetailRemoteDataSource
import com.example.movieconsumer.data.repository.detail.dataSourceImpl.DetailRemoteDataSourceImpl
import com.example.movieconsumer.data.repository.movie.dataSource.MoviesRemoteDataSource
import com.example.movieconsumer.data.repository.movie.dataSourceImpl.MoviesRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteDataModule(private val apiKey: String) {
    @Singleton
    @Provides
    fun provideMoviesRemoteDataSource(tmdbService: TMDBService): MoviesRemoteDataSource {
        return MoviesRemoteDataSourceImpl(tmdbService, apiKey)
    }

    @Singleton
    @Provides
    fun provideDetailRemoteDataSource(tmdbService: TMDBService): DetailRemoteDataSource {
        return DetailRemoteDataSourceImpl(tmdbService, apiKey)
    }
}