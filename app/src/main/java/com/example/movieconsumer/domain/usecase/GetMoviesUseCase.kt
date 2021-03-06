package com.example.movieconsumer.domain.usecase

import com.example.movieconsumer.data.model.movie.Movie
import com.example.movieconsumer.domain.repository.MoviesRepository

class GetMoviesUseCase(private val moviesRepository: MoviesRepository) {
    suspend fun execute(): List<Movie>? {
        return moviesRepository.getMovies()
    }
}