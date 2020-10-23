package com.example.movieconsumer.data.repository.movie.dataSourceImpl

import com.example.movieconsumer.data.api.TMDBService
import com.example.movieconsumer.data.model.movie.MovieList
import com.example.movieconsumer.data.repository.movie.dataSource.MoviesRemoteDataSource
import retrofit2.Response

class MoviesRemoteDataSourceImpl(
    private val tmdbService: TMDBService,
    private val apiKey: String
) : MoviesRemoteDataSource {
    override suspend fun getMovies(pageNum: Int): Response<MovieList> {
        return tmdbService.getDiscoverMovies(apiKey, pageNum)
    }
}