package com.example.movieconsumer.presentation


import android.app.Application
import com.example.movieconsumer.BuildConfig
import com.example.movieconsumer.presentation.di.Injector
import com.example.movieconsumer.presentation.di.core.*
import com.example.movieconsumer.presentation.di.detail.DetailSubComponent
import com.example.movieconsumer.presentation.di.movies.MoviesSubComponent

@Suppress("DEPRECATION")
class App : Application(), Injector {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .netModule(NetModule(BuildConfig.BASE_URL))
            .remoteDataModule(RemoteDataModule(BuildConfig.API_KEY))
            .build()
    }

    override fun createMovieSubComponent(): MoviesSubComponent {
        return appComponent.moviesSubComponent().create()
    }

    override fun createDetailSubComponent(): DetailSubComponent {
        return appComponent.detailSubComponent().create()
    }
}