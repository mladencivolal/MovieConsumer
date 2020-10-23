package com.example.movieconsumer.data.repository.movie.dataSource

import com.example.movieconsumer.data.model.movie.MovieList
import retrofit2.Response

interface MoviesRemoteDataSource {
    suspend fun getMovies(pageNum:Int): Response<MovieList>
}