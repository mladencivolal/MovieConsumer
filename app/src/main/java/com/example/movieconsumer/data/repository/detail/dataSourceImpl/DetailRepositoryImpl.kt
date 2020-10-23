package com.example.movieconsumer.data.repository.detail.dataSourceImpl

import android.annotation.SuppressLint
import android.util.Log
import com.example.movieconsumer.data.repository.detail.dataSource.DetailRemoteDataSource
import com.example.movieconsumer.domain.repository.DetailRepository
import com.example.tmdbclient.data.model.artist.Actor

class DetailRepositoryImpl(private val detailRemoteDataSource: DetailRemoteDataSource):DetailRepository {
    private var actorsList: List<Actor> = emptyList()
    override suspend fun getActors(movieId: Int): List<Actor>? {
        return getActorsFromAPI(movieId)
    }

    @SuppressLint("LogNotTimber")
    suspend fun getActorsFromAPI(movieId: Int): List<Actor> {
        try {
            val response =
                detailRemoteDataSource.getActors(movieId)

            val body = response.body()
            if (body != null) {
                actorsList = body
            }

        } catch (exception: Exception) {
            Log.i("MYTAG", exception.message.toString())
        }

        return actorsList
    }
}