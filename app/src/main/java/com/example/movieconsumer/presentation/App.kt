package com.example.movieconsumer.presentation


import android.app.Application
import com.example.movieconsumer.presentation.di.Injector
import com.example.movieconsumer.presentation.di.core.AppComponent
import com.example.movieconsumer.presentation.di.core.AppModule
import com.example.movieconsumer.presentation.di.core.NetModule
import com.example.movieconsumer.presentation.di.core.RemoteDataModule
import com.example.movieconsumer.presentation.di.detail.DetailSubComponent
import com.example.movieconsumer.presentation.di.movies.MoviesSubComponent

class App : Application(), Injector {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        @Suppress("DEPRECATION")
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .netModule(NetModule(BuildConfig.BASE_URL))
            .remoteDataModule(RemoteDataModule())
            .build()
    }

    override fun createMovieSubComponent(): MoviesSubComponent {
        return appComponent.moviesSubComponent().create()
    }

    override fun createDetailSubComponent(): DetailSubComponent {
        return appComponent.detailSubComponent().create()
    }
}