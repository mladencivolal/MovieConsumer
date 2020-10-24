package com.example.movieconsumer.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.movieconsumer.domain.usecase.GetActorsFromMovieUseCase
import com.example.movieconsumer.domain.usecase.GetTrailersForMovieUseCase

class DetailViewModel(private val getActorsFromMovieUseCase: GetActorsFromMovieUseCase, private val getTrailersForMovieUseCase: GetTrailersForMovieUseCase) :ViewModel() {
    fun getActorsFromMovie(movieId:Int) = liveData {
        val actorsList = getActorsFromMovieUseCase.execute(movieId)
        emit(actorsList)
    }
    fun getTrailersForMovie(movieId:Int) = liveData {
        val trailersList = getTrailersForMovieUseCase.execute(movieId)
        emit(trailersList)
    }
}