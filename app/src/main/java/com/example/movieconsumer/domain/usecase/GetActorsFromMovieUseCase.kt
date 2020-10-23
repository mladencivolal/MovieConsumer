package com.example.movieconsumer.domain.usecase

import com.example.movieconsumer.domain.repository.DetailRepository
import com.example.tmdbclient.data.model.artist.Actor

class GetActorsFromMovieUseCase(private val detailRepository: DetailRepository) {
    suspend fun execute(movieId: Int): List<Actor>? {
        return detailRepository.getActors(movieId)
    }
}