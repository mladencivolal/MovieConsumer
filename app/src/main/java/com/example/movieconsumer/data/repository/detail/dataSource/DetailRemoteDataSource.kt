package com.example.movieconsumer.data.repository.detail.dataSource

import com.example.tmdbclient.data.model.artist.Actor
import retrofit2.Response

interface DetailRemoteDataSource {
    suspend fun getActors(movieId: Int): Response<List<Actor>>
}