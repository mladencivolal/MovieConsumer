package com.example.movieconsumer.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.movieconsumer.data.model.movie_details.MovieDetails
import com.example.movieconsumer.domain.usecase.GetActorsFromMovieUseCase
import com.example.movieconsumer.domain.usecase.GetMovieDetailsUseCase
import com.example.movieconsumer.domain.usecase.GetTrailersForMovieUseCase

class DetailViewModel(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getActorsFromMovieUseCase: GetActorsFromMovieUseCase,
    private val getTrailersForMovieUseCase: GetTrailersForMovieUseCase
) : ViewModel() {
    suspend fun getMovieDetails(movieId: Int): MovieDetails {
        return getMovieDetailsUseCase.execute(movieId)
    }

    fun getActorsFromMovie(movieId: Int) = liveData {
        val actorsList = getActorsFromMovieUseCase.execute(movieId)
        emit(actorsList)
    }

    fun getTrailersForMovie(movieId: Int) = liveData {
        val trailersList = getTrailersForMovieUseCase.execute(movieId)
        emit(trailersList)
    }
}