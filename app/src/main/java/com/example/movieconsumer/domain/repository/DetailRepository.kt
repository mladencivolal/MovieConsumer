package com.example.movieconsumer.domain.repository

import com.example.movieconsumer.data.model.Trailer.Trailer
import com.example.movieconsumer.data.model.actor.Actor

interface DetailRepository {
    suspend fun getActors(movieId:Int): List<Actor>?
    suspend fun getTrailers(movieId:Int): List<Trailer>?
}