package com.example.movieconsumer.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieconsumer.domain.usecase.GetActorsFromMovieUseCase
import com.example.movieconsumer.domain.usecase.GetMovieDetailsUseCase
import com.example.movieconsumer.domain.usecase.GetTrailersForMovieUseCase

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getActorsFromMovieUseCase: GetActorsFromMovieUseCase,
    private val getTrailersForMovieUseCase: GetTrailersForMovieUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailViewModel(getMovieDetailsUseCase, getActorsFromMovieUseCase, getTrailersForMovieUseCase) as T
    }
}