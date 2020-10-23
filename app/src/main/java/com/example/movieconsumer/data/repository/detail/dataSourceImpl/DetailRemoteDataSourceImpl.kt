package com.example.movieconsumer.data.repository.detail.dataSourceImpl

import com.example.movieconsumer.data.api.TMDBService
import com.example.movieconsumer.data.repository.detail.dataSource.DetailRemoteDataSource
import com.example.tmdbclient.data.model.artist.Actor
import retrofit2.Response

class DetailRemoteDataSourceImpl(private val tmdbService: TMDBService, private val apiKey: String):DetailRemoteDataSource {
    override suspend fun getActors(movieId: Int): Response<List<Actor>> {
        return tmdbService.getArtistsFromMovie(apiKey, movieId)
    }

}