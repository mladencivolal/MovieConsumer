package com.example.movieconsumer.data.api

import com.example.movieconsumer.data.model.Trailer.TrailersList
import com.example.movieconsumer.data.model.actor.ActorsList
import com.example.movieconsumer.data.model.movie.MovieList
import com.example.movieconsumer.data.model.movie_details.MovieDetails
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

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<MovieDetails>

    @GET("movie/{movieId}/credits")
    suspend fun getArtistsFromMovie(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<ActorsList>

    @GET("movie/{movieId}/videos")
    suspend fun getTrailersForMovie(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<TrailersList>
}