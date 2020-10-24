package com.example.movieconsumer.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieconsumer.domain.usecase.GetActorsFromMovieUseCase
import com.example.movieconsumer.domain.usecase.GetTrailersForMovieUseCase

class DetailViewModelFactory(
    private val getActorsFromMovieUseCase: GetActorsFromMovieUseCase,
    private val getTrailersForMovieUseCase: GetTrailersForMovieUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailViewModel(getActorsFromMovieUseCase, getTrailersForMovieUseCase) as T
    }
}