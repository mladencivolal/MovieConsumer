package com.example.movieconsumer.presentation.di.detail

import com.example.movieconsumer.domain.usecase.GetActorsFromMovieUseCase
import com.example.movieconsumer.domain.usecase.GetMovieDetailsUseCase
import com.example.movieconsumer.domain.usecase.GetTrailersForMovieUseCase
import com.example.movieconsumer.presentation.detail.DetailViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class DetailModule {
    @DetailScope
    @Provides
    fun provideDetailViewModelFactory(
        getMovieDetailsUseCase: GetMovieDetailsUseCase,
        getActorsFromMovieUseCase: GetActorsFromMovieUseCase,
        getTrailersForMovieUseCase: GetTrailersForMovieUseCase
    ): DetailViewModelFactory {
        return DetailViewModelFactory(
            getMovieDetailsUseCase,
            getActorsFromMovieUseCase,
            getTrailersForMovieUseCase
        )
    }
}