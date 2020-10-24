package com.example.movieconsumer.domain.usecase

import com.example.movieconsumer.data.model.actor.Actor
import com.example.movieconsumer.domain.repository.DetailRepository

class GetActorsFromMovieUseCase(private val detailRepository: DetailRepository) {
    suspend fun execute(movieId: Int): List<Actor>? {
        return detailRepository.getActors(movieId)
    }
}