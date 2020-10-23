package com.example.movieconsumer.presentation.di.detail

import com.example.movieconsumer.domain.usecase.GetActorsFromMovieUseCase
import com.example.movieconsumer.presentation.detail.DetailViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class DetailModule {
    @DetailScope
    @Provides
    fun provideDetailViewModelFactory(
        getActorsFromMovieUseCase: GetActorsFromMovieUseCase): DetailViewModelFactory {
        return DetailViewModelFactory(getActorsFromMovieUseCase)
    }
}