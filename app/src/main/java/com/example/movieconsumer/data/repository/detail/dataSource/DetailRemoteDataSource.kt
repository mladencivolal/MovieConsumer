package com.example.movieconsumer.data.repository.detail.dataSource

import com.example.movieconsumer.data.model.Trailer.TrailersList
import com.example.movieconsumer.data.model.actor.ActorsList
import com.example.movieconsumer.data.model.movie_details.MovieDetails
import retrofit2.Response

interface DetailRemoteDataSource {
    suspend fun getMovieDetails(movieId: Int): Response<MovieDetails>
    suspend fun getActors(movieId: Int): Response<ActorsList>
    suspend fun getTrailers(movieId: Int): Response<TrailersList>
}