package br.com.popularmovies.home.di

import br.com.popularmovies.home.HomeFeatureApiImpl
import br.com.popularmovies.home.api.HomeFeatureApi
import dagger.Binds
import dagger.Module

@Module
interface HomeApiModule {

    @Binds
    fun bindsHomeFeatureApi(homeFeatureApiImpl: HomeFeatureApiImpl): HomeFeatureApi
}