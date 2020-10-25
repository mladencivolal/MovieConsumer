package com.example.movieconsumer.data.repository.detail.dataSourceImpl

import com.example.movieconsumer.data.api.TMDBService
import com.example.movieconsumer.data.model.Trailer.TrailersList
import com.example.movieconsumer.data.model.actor.ActorsList
import com.example.movieconsumer.data.model.movie_details.MovieDetails
import com.example.movieconsumer.data.repository.detail.dataSource.DetailRemoteDataSource
import retrofit2.Response

class DetailRemoteDataSourceImpl(private val tmdbService: TMDBService, private val apiKey: String) :
    DetailRemoteDataSource {
    override suspend fun getMovieDetails(movieId: Int): Response<MovieDetails> {
        return tmdbService.getMovieDetails(movieId, apiKey)
    }
    override suspend fun getActors(movieId: Int): Response<ActorsList> {
        return tmdbService.getArtistsFromMovie(movieId, apiKey)
    }
    override suspend fun getTrailers(movieId: Int): Response<TrailersList> {
        return tmdbService.getTrailersForMovie(movieId, apiKey)
    }
}