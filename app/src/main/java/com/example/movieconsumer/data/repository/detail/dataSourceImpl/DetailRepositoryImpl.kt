package com.example.movieconsumer.data.repository.detail.dataSourceImpl

import android.annotation.SuppressLint
import android.util.Log
import com.example.movieconsumer.data.model.Trailer.Trailer
import com.example.movieconsumer.data.model.actor.Actor
import com.example.movieconsumer.data.model.movie_details.MovieDetails
import com.example.movieconsumer.data.repository.detail.dataSource.DetailRemoteDataSource
import com.example.movieconsumer.domain.repository.DetailRepository
import timber.log.Timber

class DetailRepositoryImpl(private val detailRemoteDataSource: DetailRemoteDataSource):DetailRepository {
    private var actorsList: List<Actor> = emptyList()
    private var trailersList: List<Trailer> = emptyList()

    override suspend fun getMovieDetails(movieId: Int): MovieDetails {
        return getMovieDetailsFromAPI(movieId)
    }

    override suspend fun getActors(movieId: Int): List<Actor>? {
        return getActorsFromAPI(movieId)
    }

    override suspend fun getTrailers(movieId: Int): List<Trailer>? {
        return getTrailersFromAPI(movieId)
    }

    suspend fun getMovieDetailsFromAPI(movieId: Int): MovieDetails {
        lateinit var movieDetails: MovieDetails
        try {
            val response = detailRemoteDataSource.getMovieDetails(movieId)
            val body = response.body()
            if (body != null) movieDetails = body
        } catch (exception: Exception) {
            Timber.i(exception.message.toString())
        }
        return movieDetails
    }

    @SuppressLint("LogNotTimber")
    suspend fun getActorsFromAPI(movieId: Int): List<Actor> {
        try {
            val response =
                detailRemoteDataSource.getActors(movieId)
            val body = response.body()
            if (body != null) {
                actorsList = body.actors
            }

        } catch (exception: Exception) {
            Log.i("MYTAG", exception.message.toString())
        }

        return actorsList
    }

    suspend fun getTrailersFromAPI(movieId: Int):List<Trailer> {
        try {
            val response =
                detailRemoteDataSource.getTrailers(movieId)
            val body = response.body()
            if (body != null) {
                trailersList = body.trailers
            }
        } catch (exception: Exception) {
            Log.i("MYTAG", exception.message.toString())
        }
        return trailersList
    }
}