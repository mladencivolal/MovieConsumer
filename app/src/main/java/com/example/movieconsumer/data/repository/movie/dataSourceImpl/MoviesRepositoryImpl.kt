package com.example.movieconsumer.data.repository.movie.dataSourceImpl

import android.util.Log
import com.example.movieconsumer.data.model.movie.Movie
import com.example.movieconsumer.data.repository.movie.dataSource.MoviesRemoteDataSource
import com.example.movieconsumer.domain.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepositoryImpl(private val moviesRemoteDataSource: MoviesRemoteDataSource) :
    MoviesRepository {
    private var pageNum: Int = 1
    private var pageLength: Int = 50
    private var totalPages: Int = 0

    private var moviesList: List<Movie> = emptyList()

    private fun resetData() {
        pageLength = 50
        pageNum = 1
        totalPages = 0
    }

    override suspend fun getMovies(): List<Movie>? {
        return getMoviesFromAPI()
    }

    override suspend fun loadMoreMovies(): List<Movie>? {
        if (pageNum != totalPages) {
            this.pageNum++
        }
        try {
            withContext(Dispatchers.IO) {
                val response =
                    moviesRemoteDataSource.getMovies(pageNum)

                val body = response.body()
                if (body != null) {
                    moviesList = body.results
                }
            }
        } catch (exception: java.lang.Exception) {
            Log.i("MYTAG", exception.message.toString())
        }
        return moviesList
    }

    private suspend fun getMoviesFromAPI(): List<Movie> {
        resetData()
        try {
            val response =
                moviesRemoteDataSource.getMovies(pageNum)

            var body = response.body()

            if (body != null) {
                moviesList = body.results
                totalPages = body.totalPages
            }
        } catch (exception: Exception) {
            Log.i("MYTAG", exception.message.toString())
        }
        return moviesList
    }

}