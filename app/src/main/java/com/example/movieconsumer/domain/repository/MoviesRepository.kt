package com.example.movieconsumer.domain.repository

import com.example.movieconsumer.data.model.movie.Movie

interface MoviesRepository {
    suspend fun getMovies(): List<Movie>?
    suspend fun loadMoreMovies(): List<Movie>?
}