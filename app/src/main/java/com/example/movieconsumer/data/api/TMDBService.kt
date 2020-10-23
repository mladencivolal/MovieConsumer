package com.example.movieconsumer.data.api

import com.example.movieconsumer.data.model.movie.MovieList
import com.example.tmdbclient.data.model.artist.Actor
import com.example.tmdbclient.data.model.artist.ActorList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBService {
    @GET("discover/movie")
    suspend fun getDiscoverMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<MovieList>

    @GET("movie/{movieId}/credits}")
    suspend fun getArtistsFromMovie(
        @Query("api_key") apiKey: String,
        @Query("movieId") movieId: Int
    ): Response<List<Actor>>
}