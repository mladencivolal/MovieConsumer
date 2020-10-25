package com.example.movieconsumer.domain.usecase

import com.example.movieconsumer.data.model.movie_details.MovieDetails
import com.example.movieconsumer.domain.repository.DetailRepository

class GetMovieDetailsUseCase(private val detailRepository: DetailRepository) {
    suspend fun execute(movieId:Int): MovieDetails {
        return detailRepository.getMovieDetails(movieId)
    }
}