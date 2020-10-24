package com.example.movieconsumer.domain.usecase

import com.example.movieconsumer.data.model.Trailer.Trailer
import com.example.movieconsumer.domain.repository.DetailRepository

class GetTrailersForMovieUseCase(private val detailRepository: DetailRepository) {
    suspend fun execute(movieId: Int): List<Trailer>? {
        return detailRepository.getTrailers(movieId)
    }
}