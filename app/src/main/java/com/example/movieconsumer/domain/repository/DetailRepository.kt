package com.example.movieconsumer.domain.repository

import com.example.tmdbclient.data.model.artist.Actor

interface DetailRepository {
    suspend fun getActors(movieId:Int): List<Actor>?
}