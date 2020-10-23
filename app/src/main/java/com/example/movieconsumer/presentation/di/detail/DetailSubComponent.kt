package com.example.movieconsumer.presentation.di.detail

import com.example.movieconsumer.presentation.detail.DetailActivity
import dagger.Subcomponent

@DetailScope
@Subcomponent(modules = [DetailModule::class])
interface DetailSubComponent {
    fun inject(detailActivity: DetailActivity)

    @Subcomponent.Factory
    interface Factory {
        fun create():DetailSubComponent
    }
}