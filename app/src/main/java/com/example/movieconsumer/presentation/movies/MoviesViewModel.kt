package com.example.movieconsumer.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.movieconsumer.data.model.movie.Movie
import com.example.movieconsumer.domain.usecase.GetMoviesUseCase
import com.example.movieconsumer.domain.usecase.LoadMoreMoviesUseCase

class MoviesViewModel(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val loadMoreMoviesUseCase: LoadMoreMoviesUseCase
):ViewModel() {
    fun getMovies() = liveData {
        val moviesList: List<Movie>? = getMoviesUseCase.execute()
        emit(moviesList)
    }

    fun loadMoreMovies() = liveData {
        val moviesList = loadMoreMoviesUseCase.execute()
        emit(moviesList)
    }
}