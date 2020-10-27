package com.example.movieconsumer.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieconsumer.domain.usecase.GetMoviesUseCase
import com.example.movieconsumer.domain.usecase.LoadMoreMoviesUseCase

@Suppress("UNCHECKED_CAST")
class MoviesViewModelFactory(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val loadMoreMoviesUseCase: LoadMoreMoviesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MoviesViewModel(getMoviesUseCase, loadMoreMoviesUseCase) as T
    }
}