package com.example.movieconsumer.domain.repository

import com.example.movieconsumer.data.model.Trailer.Trailer
import com.example.movieconsumer.data.model.actor.Actor
import com.example.movieconsumer.data.model.movie_details.MovieDetails

interface DetailRepository {
    suspend fun getMovieDetails(movieId:Int): MovieDetails
    suspend fun getActors(movieId:Int): List<Actor>?
    suspend fun getTrailers(movieId:Int): List<Trailer>?
}