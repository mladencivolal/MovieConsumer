package com.example.movieconsumer.presentation.di.core

import com.example.movieconsumer.presentation.di.detail.DetailSubComponent
import com.example.movieconsumer.presentation.di.movies.MoviesSubComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetModule::class,
        UseCaseModule::class,
        RepositoryModule::class,
        RemoteDataModule::class]
)
interface AppComponent {
    fun moviesSubComponent():MoviesSubComponent.Factory
    fun detailSubComponent(): DetailSubComponent.Factory
}